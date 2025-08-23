pipeline {
    agent any

    stages {
        stage('pull'){
            steps{
                git branch: 'main', url: 'https://github.com/SurajBele/studentapp.ui.git'
                echo 'Pulling Successfully..'
            }
        }
        
        stage('Build') {
            steps {
                // sh 'mvn clean package'
                sh '''
                export MAVEN_HOME=/opt/apache-maven-3.9.10
                export MAVEN_HOME
                PATH=$PATH:$MAVEN_HOME/bin
                mvn -v
                mvn clean package
                '''
                echo 'Building Successfully..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing Successfully..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying Successfully....'
            }
        }
    }
}