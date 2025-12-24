node {
  
    stage('Clone') {
        git credentialsId: 'jenkins', url: 'git@gitlab.com:f4045/projet_j2ee.git'

    }
    stage('SonarQube analysis') {
        withSonarQubeEnv {
            def mavenHome = tool name: "Maven", type: "maven"
            def mavenCMD = "${mavenHome}/bin/mvn"
            sh "${mavenCMD} clean package sonar:sonar"
        }
    }

    stage('Quality Gate') {
        waitForQualityGate abortPipeline: true

    }
    stage('Build') {
        def mavenHome = tool name: "Maven", type: "maven"
        def mavenCMD = "${mavenHome}/bin/mvn"
        sh "${mavenCMD} clean package"
    }
    
    stage('deploy')
        deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://3.72.207.31:8080/')], contextPath: null, war: '**/*.war'


}
