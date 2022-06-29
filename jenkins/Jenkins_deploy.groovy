pipeline {
    agent any

    // environment {
    //     TOKEN     = 'TOKEN'
    //
    // }

   stages() {
     stage('Test1' ) {
       steps {
         script {
           sh 'echo HELLO'

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
