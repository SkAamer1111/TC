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
                    docker run -d --name health-app -p 8080:8080 $IMAGE_NAME:1.0 > deploy.log 2>&1
                '''
            }
        }

        stage('Upload Logs To S3') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-secret-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    sh '''
                        aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
                        aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
                        aws configure set region ap-southeast-2

                        aws s3 cp deploy.log s3://deployment-tc-logs/deploy-$BUILD_NUMBER.log
                    '''
                }
            }
        }
    }
}
