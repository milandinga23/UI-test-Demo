pipeline {
    agent any

    tools {
        maven 'Maven 3.9.8'
    }

    environment {
        SCREENSHOT_DIR = 'target/screenshots'
        MAVEN_OPTS = '-Dfile.encoding=UTF-8'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "🔄 Sťahujem kód z Gitu"
                git 'https://github.com/milandinga23/UI-test-Demo'
            }
        }

        stage('Parallel Tests') {
            parallel {
                stage('LoginTest') {
                    steps {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withEnv(['JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8']) {
                                bat 'mvn -Dtest=LoginTest test'
                            }
                        }
                    }
                }
                stage('AddAndVerifyEmployeeTest') {
                    steps {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withEnv(['JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8']) {
                                bat 'mvn -Dtest=LoginTest test'
                            }
                        }
                    }
                }
                stage('LoginAndCheckMyInfoTest') {
                    steps {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withEnv(['JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8']) {
                                bat 'mvn -Dtest=LoginTest test'
                            }
                        }
                    }
                }
            }
        }

        stage('Test Reports') {
            steps {
                echo "🧪 Zbieram JUnit reporty"
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Archive Screenshots') {
            steps {
                script {
                    def screenshotFolder = "${env.SCREENSHOT_DIR}"
                    if (fileExists(screenshotFolder)) {
                        echo "🖼️ Archivujem screenshoty z chybových testov"
                        archiveArtifacts artifacts: "${screenshotFolder}/**/*.png", allowEmptyArchive: true
                    } else {
                        echo "⚠️ Žiadne screenshoty neboli nájdené"
                    }
                }
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline dokončená"
        }
        failure {
            echo "❌ Testy zlyhali! Pozri reporty alebo screenshoty."
        }
    }
}
