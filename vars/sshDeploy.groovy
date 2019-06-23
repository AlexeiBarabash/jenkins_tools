#!/usr/bin/env groovy
def call(sshServer, sshUser = 'deploy') {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    scriptFile = "./script.sh"
    sh """
        echo '
        docker pull ${CONTAINER_NAME_REPO} || exit 1
        docker stop ${CONTAINER_NAME} || true
        docker rm ${CONTAINER_NAME} || true
        echo 'docker run command'
        docker run ${CONTAINER_RUN_ARGS} --name ${CONTAINER_NAME} ${CONTAINER_NAME_REPO} || exit 1
        docker ps -a
        ' > ${scriptFile}
    """
    sh "ssh ${sshUser}@${sshServer} bash -s < ${scriptFile}"
    textWithColor("Finished Deploy Container")
}