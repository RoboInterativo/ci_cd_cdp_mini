pipeline {
    agent any

    // environment {
    //     TOKEN     = 'TOKEN'
    //
    // }
parameters {
    string(
     name: 'CODE_REPO',
     defaultValue: '',
     description: 'CODE_REPO'
   )
   string(
    name: 'BRANCH',
    defaultValue: 'feature/init',
    description: 'BRANCH'
  )

 }

   stages() {
     stage('Test1' ) {
       steps {
         script {
           sh 'echo HELLO'

           checkout([$class: 'GitSCM', branches: [[name: BRANCH]],
           doGenerateSubmoduleConfigurations: false,
           extensions: [[$class: 'RelativeTargetDirectory',
           relativeTargetDir: 'app/']], gitTool: 'Default',
           submoduleCfg: [], userRemoteConfigs: [[credentialsId: 	'ssh_for_jenkins',
           url: CODE_REPO]]])


         }
        }
      }
   }
   post {
       success {

           archiveArtifacts allowEmptyArchive: true, artifacts: '${WORKSPACE}/simple-back-front/front/*.zip', fingerprint: true

       }
     }

 }
