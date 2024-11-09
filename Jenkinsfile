pipeline {
    environment {
        registry = "msaidc/timesheet"
        registryCredential = 'dockerCred'
        dockerImage = ''
        awsCredentialsId = 'aws-credentials' // Jenkins AWS credentials ID for Access Key and Secret Key
        awsSessionTokenId = 'aws-session-token' // Jenkins credential ID for AWS Session Token
        awsRegion = 'us-east-1' // Replace with your AWS region
        eksCluster = 'mykubernetes' // Replace with your EKS cluster name

    }

    agent any

    stages {
        stage('CHECKOUT GIT') {
            steps {
                git branch: 'feature/said',
                    credentialsId: 'accessGit',
                    url: 'https://github.com/sondeshakiri/GL5-G2-ProjetTimesheet.git'
            }
        }

        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean package -Ptests'
            }
        }

        stage('ARTIFACT CONSTRUCTION') {
            steps {
                echo 'ARTIFACT CONSTRUCTION...'
                sh 'mvn package -Ptests'
            }
        }

        stage('UNIT TESTS and Coverage') {
            steps {
                echo 'Launching Unit Tests...'
                sh 'mvn clean verify -Ptests'
            }
        }
    stage('MVN SONARQUBE'){
                steps {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=admin -Dsonar.password="Admin55307062."'

                    }

                }

      /*  stage('PUBLISH SNAPSHOT TO NEXUS') {
            steps {
                sh """
                    mvn deploy -DaltDeploymentRepository=snapshotRepo::default::http://admin:55307062Said@nexus:8081/repository/maven-snapshots/ -Ptests
                """
            }
        }*/

        //Uncomment this stage if you want to publish releases
        stage('PUBLISH RELEASE TO NEXUS') {
            steps {
                sh """
                    mvn deploy -DaltDeploymentRepository=releaseRepo::default::http://admin:55307062Said@nexus:8081/repository/maven-releases/ -Ptests
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
                        dockerImage.push('latest')
                    }
                }
            }
        }

        stage('REPORT METRICS') {
            steps {
                script {
                    // Example: Push a custom metric to Prometheus Pushgateway
                    sh """
                        echo 'jenkins_builds_total{status="success"} 1' | curl --data-binary @- http://promgateway:9091/metrics/job/jenkins
                    """
                }
            }
        }


        // Terraform stages
        stage('Terraform Init') {
            steps {
                dir('terraform') {
                    withCredentials([
                        usernamePassword(
                            credentialsId: "${awsCredentialsId}",
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        ),
                        string(credentialsId: "${awsSessionTokenId}", variable: 'AWS_SESSION_TOKEN')
                    ]) {
                        sh 'terraform init -input=false'
                    }
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                dir('terraform') {
                    withCredentials([
                        usernamePassword(
                            credentialsId: "${awsCredentialsId}",
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        ),
                        string(credentialsId: "${awsSessionTokenId}", variable: 'AWS_SESSION_TOKEN')
                    ]) {
                        sh 'terraform plan -input=false -out=tfplan'
                    }
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                dir('terraform') {
                    withCredentials([
                        usernamePassword(
                            credentialsId: "${awsCredentialsId}",
                            usernameVariable: 'AWS_ACCESS_KEY_ID',
                            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                        ),
                        string(credentialsId: "${awsSessionTokenId}", variable: 'AWS_SESSION_TOKEN')
                    ]) {
                        sh 'terraform apply -input=false -auto-approve tfplan'
                    }
                }
            }
        }

        stage('Configure AWS CLI') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: "${awsCredentialsId}",
                        usernameVariable: 'AWS_ACCESS_KEY_ID',
                        passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                    ),
                    string(credentialsId: "${awsSessionTokenId}", variable: 'AWS_SESSION_TOKEN')
                ]) {
                    sh '''
                        #!/bin/bash
                        set -e

                        # Configure AWS CLI with credentials
                        aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
                        aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
                        aws configure set aws_session_token $AWS_SESSION_TOKEN
                        aws configure set default.region ${awsRegion}

                        # Update kubeconfig to use EKS cluster
                        aws eks update-kubeconfig --region ${awsRegion} --name ${eksCluster}
                    '''
                }
            }
        }

        // Apply Kubernetes Deployment and Service

        stage('Apply MariaDB Deployment') {
            steps {
                script {
                    // Apply MariaDB Deployment
                    sh 'kubectl apply -f mariadb-deployment.yaml'

                    // Wait for MariaDB pod to be running
                }
            }
        }

        stage('Apply App Deployment') {
            steps {
                script {
                    // Apply Application Deployment
                    sh 'kubectl apply -f deployment.yaml'

                    // Wait for application pod to be running
                     sh 'kubectl apply -f service.yaml'

                }
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
