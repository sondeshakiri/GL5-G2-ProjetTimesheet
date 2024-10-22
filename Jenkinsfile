pipeline {

    environment {
        registry = "msaidc/timesheet"
        registryCredential = 'dockerCred'
        dockerImage = ''
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
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }

        stage('UNIT TESTS') {
            steps {
                echo 'Launching Unit Tests...'
                sh 'mvn test'
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                sh """
                                mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=admin -Dsonar.password="Admin55307062." \
                                -Dsonar.java.coveragePlugin=jacoco \
                                -Dsonar.jacoco.reportPaths=target/jacoco.exec \
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
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
    }
}
