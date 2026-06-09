pipeline{
    
    agent any
    
    tools {
        maven 'maven3'
        jdk 'jdk17'
    }
    environment {
        IMAGE_NAME = 'shaikhaamer/test'
    }

    stages{
        stage ("CODE") {
        steps{

            git url: 'https://github.com/SkAamer1111/TC.git ', branch: 'main'            
            }
        }
        stage ('CODE BUILD'){
            steps{
                sh 'mvn clean package'
            }
        }
        stage ('ARCHIVE'){
            steps{
                archiveArtifacts artifacts: 'target/*.jar' 
            }
        }
        stage ('TEST_CODE'){
            steps{
                sh 'mvn test'
            }
        }
        stage ('BUILD-IMAGE'){
            steps{
                sh 'docker image build -t $IMAGE_NAME:1.0 .'
            }
        }
        stage ('PUSH IMAGE TO GITHUB'){
            steps{
                withCredentials([
                    usernamePassword(
                        credentialsId: 'docker-token',
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD' 
                    )
                ]) {
                    sh """ echo $PASSWORD | docker login -u $USERNAME --password-stdin """
                    sh """ docker push $IMAGE_NAME:1.0 """
                        
                }
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    docker stop health-app || true
                    docker rm health-app || true
                    docker run -d --name health-app -p 8081:8080 $IMAGE_NAME:1.0
                    sleep 10s
                    curl http://localhost:8081/health
                    docker container rm -f health-app
                '''
            }
        }
        stage('TRIGGER'){
            steps{
                build job:'pipelineB'
            }
        }

    }
}
