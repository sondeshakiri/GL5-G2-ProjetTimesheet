pipeline {

    environment {
        registry = "msaidc/timesheet"
        registryCredential = 'dockerCred'
        dockerImage = ''
        awsCredentialsId = 'aws-credentials' // Jenkins AWS credentials ID for Access Key and Secret Key
        awsSessionTokenId = 'aws-session-token' // Jenkins credential ID for AWS Session Token
        awsRegion = 'us-east-1' // Replace with your AWS region
        eksCluster = 'devops' // Replace with your EKS cluster name
    }

    agent any

    stages {
        stage('CHECKOUT GIT') {
            steps {
                git branch: 'said',
                    credentialsId: 'accessGit',
                    url: 'https://github.com/sondeshakiri/GL5-G2-ProjetTimesheet.git'
            }
        }

        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean -DargLine="--add-opens jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED"'
            }
        }

        stage('ARTIFACT CONSTRUCTION') {
            steps {
                echo 'ARTIFACT CONSTRUCTION...'
                sh 'mvn package'
            }
        }

        stage('UNIT TESTS and Coverage') {
            steps {
                echo 'Launching Unit Tests...'
                sh 'mvn clean verify -Ptest-coverage'
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                sh """
                                mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=admin -Dsonar.password="Admin55307062." \
                                -Dsonar.java.coveragePlugin=jacoco \
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                                -Dsonar.jacoco.reportPaths=target/jacoco.exec
                            """
                sh ''
            }
        }

        stage('PUBLISH TO NEXUS') {
            steps {
                sh """
                    mvn -X deploy \
                    -DaltDeploymentRepository=deploymentRepo::default::http://admin:55307062Said@nexus:8081/repository/maven-releases/
                """
            }
        }

        stage('BUILDING OUR IMAGE') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }

        stage('DEPLOY OUR IMAGE') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
           stage('Apply Kubernetes Deployment and Service') { // New Stage to Apply Deployment
                    steps {
                        withCredentials([
                            usernamePassword(credentialsId: "${awsCredentialsId}", usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY'),
                            string(credentialsId: "${awsSessionTokenId}", variable: 'AWS_SESSION_TOKEN')
                        ]) {
                            sh """
                                #!/bin/bash
                                set -e

                                # Configure AWS CLI with credentials
                                aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
                                aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
                                aws configure set aws_session_token $AWS_SESSION_TOKEN
                                aws configure set default.region ${awsRegion}

                                # Update kubeconfig to use EKS cluster
                                aws eks update-kubeconfig --region ${awsRegion} --name ${eksCluster}
                                kubectl get nodes
                                kubectl apply -f service.yaml
                                kubectl apply -f deployment.yaml
                            """
                        }
                    }
                }
        post {
                success {
                    mail to: 'chaiebsaid.01@gmail.com',
                         subject: "Jenkins Pipeline Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                         body: "The Jenkins pipeline ${env.JOB_NAME} completed successfully.\n\nBuild URL: ${env.BUILD_URL}"
                }
                failure {
                    mail to: 'chaiebsaid.01@gmail.com',
                         subject: "Jenkins Pipeline Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                         body: "The Jenkins pipeline ${env.JOB_NAME} has failed.\n\nBuild URL: ${env.BUILD_URL}"
                }
    }
}
