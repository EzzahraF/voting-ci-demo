pipeline {
  agent any
  environment {
    MAVEN_OPTS = '-Xmx1024m'
    SONAR_HOST_URL = credentials('SONAR_HOST')
    SONAR_TOKEN = credentials('SONAR_TOKEN')
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build') {
      steps {
        sh 'mvn -B -e clean package'
      }
      post {
        success { archiveArtifacts artifacts: 'target/*.jar', fingerprint: true }
      }
    }
    stage('Unit Tests') {
      steps { sh 'mvn -B test' }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }
    stage('Code Coverage') {
      steps {
        sh 'mvn -B test jacoco:report'
        publishHTML(target: [
          reportName: 'JaCoCo Coverage',
          reportDir: 'target/site/jacoco',
          reportFiles: 'index.html',
          keepAll: true
        ])
      }
    }
    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('sonar') {
          sh "mvn -B sonar:sonar -Dsonar.projectKey=voting-ci-demo -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}"
        }
      }
    }
    stage('Archive and Cleanup') {
      steps {
        archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
        cleanWs()
      }
    }
  }
}
