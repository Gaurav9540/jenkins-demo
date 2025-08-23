pipeline {
    agent any

    stages {
        stage('pull'){
            steps{
                echo 'Pulling Successfully..'
            }
        }
        
        stage('Build') {
            steps {
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