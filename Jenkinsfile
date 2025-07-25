pipeline {
    agent any
    tools {
        maven 'Maven 3'
    }

    environment {
        PATH = "/usr/local/bin:/opt/homebrew/bin:${env.PATH}"
        AWS_REGION = 'us-east-1'
        ECR_REPO = '730335674924.dkr.ecr.us-east-1.amazonaws.com/important-design-pattern'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/jay-prakash96/important-design-pattern.git'
            }
        }
        stage('Debug Maven') {
            steps {
                sh 'echo $PATH'
                sh 'mvn -version'
            }
        }
        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Debug Docker') {
            steps {
                sh 'echo $PATH'
                sh 'which docker'
                sh 'docker --version'
            }
        }
        stage('Docker Build') {
            steps {
                sh "docker buildx build --platform linux/amd64 -t $ECR_REPO:$IMAGE_TAG --push ."
            }
        }
        stage('Docker Push') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-jenkins-creds']]) {
                    sh """
                        aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO
                        docker push $ECR_REPO:$IMAGE_TAG
                    """
                }
            }
        }
        stage('Substitute Image in YAML') {
            steps {
                sh """
                  sed -i '' 's|image: .*|image: $ECR_REPO:$IMAGE_TAG|' k8s/deployment.yaml
                """
            }
        }
        stage('K8s Deploy') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-jenkins-creds']]) {
                    sh '''
                        aws eks --region $AWS_REGION update-kubeconfig --name design-pattern-cluster
                        kubectl apply -f k8s/deployment.yaml
                        kubectl apply -f k8s/service.yaml
                        kubectl apply -f k8s/ingress.yaml
                    '''
                }
            }
        }
    }
}
