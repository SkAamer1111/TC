pipeline{
    
    agent any
    
    triggers {
        githubPush()
    }

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

            git url: 'https://github.com/SkAamer1111/java-spring-boot-project.git ', branch: 'main'            
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
        stage ('DEPLOY-APP'){
            steps {
                sh 'docker container run -d --name deploy -P $IMAGE_NAME:1.0'
            }
        }
    }
}
