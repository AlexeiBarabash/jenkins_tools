#!/usr/bin/env groovy
// ssh deploy a docker image, ex: env.CONTAINER_NAME_REPO = redis, env.CONTAINER_NAME = my-redis, env.CONTAINER_RUN_ARGS from buildContainer()
def call(sshServer, sshUser = 'deploy') {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    def script = """
        docker pull ${env.CONTAINER_NAME_REPO} || exit 1
        docker stop ${env.CONTAINER_NAME} || true
        docker rm ${env.CONTAINER_NAME} || true
        echo 'docker run command'
        docker run ${env.CONTAINER_RUN_ARGS} --name ${env.CONTAINER_NAME} ${env.CONTAINER_NAME_REPO} || exit 1
        docker ps -a
    """
    runScriptOverSSH(script,sshServer,sshUser)
    textWithColor("Finished Deploy Container")
}
