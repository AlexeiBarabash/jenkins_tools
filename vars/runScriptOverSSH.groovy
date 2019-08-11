#!/usr/bin/env groovy
def call(script,sshServer, sshUser = 'deploy') {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    def scriptFile = "./script.sh"
    sh """
        echo '
        ${script}
        ' > ${scriptFile}
    """
    sh "ssh ${sshUser}@${sshServer} bash -s < ${scriptFile}"
    textWithColor("Finished Deploy Container")
}
