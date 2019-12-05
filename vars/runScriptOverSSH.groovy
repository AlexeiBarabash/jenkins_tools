#!/usr/bin/env groovy
def call(script,sshServer, sshUser = 'deploy', pass = '') {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    bashCommand("""
        echo '
        ${script}
        ' > ./script.sh
    """)
    bashCommand("${pass != '' ? "sshpass -p ${pass} " : ""} ssh -o StrictHostKeyChecking=no ${sshUser}@${sshServer} bash -s < ./script.sh")
    textWithColor("Finished Deploy Container")
}
