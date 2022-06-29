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
           // git clone CODE_REPO -b BRANCH app/

           checkout([$class: 'GitSCM', branches: [[name: BRANCH]],
           doGenerateSubmoduleConfigurations: false,
           extensions: [[$class: 'RelativeTargetDirectory',
           relativeTargetDir: 'app/']], gitTool: 'Default',
           submoduleCfg: [], userRemoteConfigs: [[credentialsId: 	'ssh_key',
           url: CODE_REPO]]])

           withCredentials([sshUserPrivateKey(credentialsId: CREDS,
                                             keyFileVariable: 'JENKINS_PRIVATE_KEY', passphraseVariable: 'PASSPHRASE',
                                              usernameVariable: 'USERNAME')]) {

           playbook_name = "deploy.yml"
           tags='front'
           ansiblePlaybook extras:   "-u root --private-key ${JENKINS_PRIVATE_KEY} -vv --extra-vars  \" workspace=${WORKSPACE}    ssh_key=${JENKINS_PRIVATE_KEY} inventory_dir=\"inventories/dev/\"\" ",
           installation: 'ansible29',
           inventory: "${WORKSPACE}/ansible/inventories/dev/inventory",
           playbook: "${WORKSPACE}/ansible/${playbook_name}"




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
