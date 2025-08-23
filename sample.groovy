pipeline {
    agent any

    environment {
        PATH = "/opt/apache-maven-3.9.10/bin:${PATH}"
    }

    stages {
        stage('pull'){
            steps{
                git branch: 'main', url: 'https://github.com/SurajBele/studentapp.ui.git'
                echo 'Pulling Successfully..'
            }
        }
        
        stage('Build') {
            dir('student-app') { 
                sh 'mvn clean package'
            }
            echo "Building Successfully.."
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