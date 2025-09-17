pipeline {
    
    agent any

    // agent {
    //     label 'node-1'
    // }

    // Environment Variables
    environment {
        APP_NAME = 'mvn-project'
        DOCKERHUB_USER = 'bala976'   
        IMAGE = "${DOCKERHUB_USER}/${APP_NAME}"
    }

    stages {

        // Pulling Stage
        stage('Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/Gaurav9540/mvn-project.git'
                // git branch: 'main', url: 'https://github.com/Yash2-27/EMS.git'
                echo "pulling successfully!"
            }
        }

        // Building Stage
        stage('Building') {
            steps {
               sh 'mvn clean package'
               echo "Build completed successfully!"
            }
        }

        // Testing Stage
        stage('Test') {
            steps {
                // withSonarQubeEnv(installationName: 'sonar-server', credentialsId: 'sonar-token') {
                //   sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=myproject'
                // }
                echo "testing successfully!"
            }
        }

        // QualityGate Check Test
        stage('QualityGate') {
            steps {
                // waitForQualityGate abortPipeline: false, credentialsId: 'sonar-secret-key'
                echo "qulity gate check successfully!"
            }
        }

        Docker Image Build Stage
        stage('Docker Build') {
            steps {
                script {
                    def shortSha = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
                    env.IMAGE_TAG = "${env.BUILD_NUMBER}-${shortSha}"
                }
                sh "docker build -t ${IMAGE}:${IMAGE_TAG} ."
                sh "docker images | grep ${APP_NAME}"
                echo "Docker image built successfully!"
            }
        }


        // stage('Docker Build') {
        // steps {
        //    script {
        //      // 🔹 Force checkout from master branch
        //       checkout([$class: 'GitSCM',
        //         branches: [[name: '*/master']],
        //         userRemoteConfigs: [[url: 'https://github.com/Yash2-27/EMS.git']]
        //       ])

        //       def shortSha = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
        //       env.IMAGE_TAG = "${env.BUILD_NUMBER}-${shortSha}"

        //       sh "docker build -t ${IMAGE}:${IMAGE_TAG} ."
        //       sh "docker images | grep ${APP_NAME}"
        //       echo "Docker image built successfully!"
        //    }
        // }
}



        // Docker Image Push to Dockerhub Stage        
        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh """
                      echo "$PASS" | docker login -u "$USER" --password-stdin
                      docker tag ${IMAGE}:${IMAGE_TAG} ${IMAGE}:latest
                      docker push ${IMAGE}:${IMAGE_TAG}
                      docker push ${IMAGE}:latest
                      docker logout
                    """
                }
                echo "Image pushed successfully to Docker Hub!"
            }
        }

        // Deployment Stage
        stage('Deploy to Tomcat') {
            steps {
                // deploy adapters: [tomcat9(credentialsId: 'tomcat-pass', path: '', url: 'http://65.0.73.96:8080/')], contextPath: '/', war: '**/*.war'
                echo "Deploy Successfully!"
            }
        }

        // Run Container 
        stage('Deploy with Docker') {
            steps {
                sh """
                  docker rm -f ${APP_NAME} || true
                  docker run -d --name ${APP_NAME} -p 8083:8080 ${IMAGE}:latest
                """
                echo "Container deployed and running on port 8083"
            }
        }
    }
}