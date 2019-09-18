#!/usr/bin/env groovy
def call(script,sshServer, sshUser = 'deploy', pass = '') {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    def scriptFile = "./script.sh"
    sh """
        echo '
        ${script}
        ' > ${scriptFile}
    """
    def cmd = "ssh ${sshUser}@${sshServer} bash -s < ${scriptFile}"
    if(pass != '') {
        cmd = "sshpass -p ${pass} " + cmd + " -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null"
    }
    sh cmd
    textWithColor("Finished Deploy Container")
}
