pipeline {
    agent any

    stages {
        stage('pull'){
            steps{
                git branch: 'main', url: 'https://github.com/Gaurav9540/spotify-clone.git'
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