#!/usr/bin/env groovy
def call(script,sshServer, sshUser = 'deploy', pass = '', isWindows = false) {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    sh """
        echo '
        ${script}
        ' > ./script.sh
    """
    sh "${pass != '' ? "sshpass -p ${pass} " : ""} ssh ${sshUser}@${sshServer} ${isWindows ? 'cmd' : 'bash'} -s < ./script.sh -o StrictHostKeyChecking=no"
    textWithColor("Finished Deploy Container")
}
