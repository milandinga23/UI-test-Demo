pipeline {
    agent any

    tools {
        maven 'Maven 3.9.8'
    }

    environment {
        SCREENSHOT_DIR = 'target/screenshots'
    }

    stages {
        stage('Checkout') {
            steps {
                bat 'chcp 65001'
                echo "🔄 Sťahujem kód z Gitu"
                git 'https://github.com/milandinga23/UI-test-Demo'
            }
        }

        stage('Build & Test') {
            steps {
                echo "🏗️ Buildujem a spúšťam testy"
                bat 'mvn clean test'
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
