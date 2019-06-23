#!/usr/bin/env groovy
def call(sshServer, sshUser = 'deploy') {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    def scriptFile = "./script.sh"
    sh """
        echo '
        docker pull ${env.CONTAINER_NAME_REPO} || exit 1
        docker stop ${env.CONTAINER_NAME} || true
        docker rm ${env.CONTAINER_NAME} || true
        echo 'docker run command'
        docker run ${env.CONTAINER_RUN_ARGS} --name ${env.CONTAINER_NAME} ${env.CONTAINER_NAME_REPO} || exit 1
        docker ps -a
        ' > ${scriptFile}
    """
    sh "ssh ${sshUser}@${sshServer} bash -s < ${scriptFile}"
    textWithColor("Finished Deploy Container")
}