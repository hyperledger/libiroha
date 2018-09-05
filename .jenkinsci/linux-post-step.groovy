def linuxPostStep() {
  timeout(time: 600, unit: "SECONDS") {
    try {
      // if (currentBuild.currentResult == "SUCCESS" && env.GIT_LOCAL_BRANCH ==~ /(master|develop)/) {
        def artifacts = load ".jenkinsci/artifacts.groovy"
        def commit = env.GIT_COMMIT
        def platform = sh(script: 'uname -m', returnStdout: true).trim()
        filePaths = [ '/tmp/${GIT_COMMIT}-${BUILD_NUMBER}/*' ]
        sh "ls -al /tmp/${GIT_COMMIT}-${BUILD_NUMBER}/" 
        artifacts.uploadArtifacts(filePaths, sprintf('libiroha/linux/%4$s/%1$s-%2$s-%3$s', ["develop", sh(script: 'date "+%Y%m%d"', returnStdout: true).trim(), commit.take(6), platform]))
      // }
    }
    finally {
      def cleanup = load ".jenkinsci/docker-cleanup.groovy"
      cleanup.doDockerCleanup()
      // cleanWs()
    }
  }
}

return this
