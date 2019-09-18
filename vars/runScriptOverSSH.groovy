#!/usr/bin/env groovy
def call(script,sshServer, sshUser = 'deploy', pass = '', isWindows = false) {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    sh """
        echo '
        ${script}
        ' > ./script.sh
    """
    def cmd = "ssh ${sshUser}@${sshServer} ${isWindows ? 'cmd' : 'bash'} -s < ./script.sh -o StrictHostKeyChecking=no"
    if(pass != '') {
        cmd = "sshpass -p ${pass} " + cmd
    }
    sh cmd
    textWithColor("Finished Deploy Container")
}
