def getImages (APPS) {
  return "APPS"
}

 parameters {
   choice(
         choices: getImages( APPS ),
         name: 'CODE_REPO'
     )
 }

pipeline {
    agent any
    environment {
           CREDS= 'ssh_key'
    //     VDS_TOKEN     = credentials('clo')

    //
     }







    parameters {




                string(
                 name: 'CODE_REPO',
                 defaultValue: 'git@github.com:RoboInterativo/robointerativo_site.git',
                 description: ''

              )
      string(
       name: 'BRANCH',
       defaultValue: 'feature/init',
       description: 'BRANCH'
     )

     // booleanParam(
     //   name: 'DOCKER',
     //   defaultValue: true,
     //   description: 'Build image'
     // )

     }

      stages() {

          stage('SCM c' ) {
          steps {
            script {
              println "TEST "
              sh 'echo TEST BASH'


              println "Use APP=${params.APPS} CODE_REPO=${CODE_REPO}"
              // sh "[ -d ${WORKSPACE}/app ] && rm ${WORKSPACE}/app -rf"
              //
              // sh "[ -f ${WORKSPACE}/app.zip ] && rm ${WORKSPACE}/app.zip"

               checkout([$class: 'GitSCM', branches: [[name: BRANCH]],
               doGenerateSubmoduleConfigurations: false,
               extensions: [[$class: 'RelativeTargetDirectory',
               relativeTargetDir: 'app/']], gitTool: 'Default',
               submoduleCfg: [], userRemoteConfigs: [[credentialsId: CREDS,
               url: CODE_REPO]]])




              // checkout([$class: 'GitSCM', branches: [[name: BRANCH]],
              //     extensions: [[$class: 'RelativeTargetDirectory',
              //     relativeTargetDir: 'app/']],
              //     userRemoteConfigs: [[credentialsId: CREDS, url: CODE_REPO]]
              //   ])


              sh "echo archiveAPP  && ls app && zip ${WORKSPACE}/ansible/files/app.zip app -r"
            }
          }
        }

    //



      }








    post {
        success {

            archiveArtifacts allowEmptyArchive: true, artifacts: '${WORKSPACE}/simple-back-front/front/*.zip', fingerprint: true

        }
      }
}
