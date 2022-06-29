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
    description: 'CODE_REPO')

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
           submoduleCfg: [], userRemoteConfigs: [[credentialsId: CREDS,
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
