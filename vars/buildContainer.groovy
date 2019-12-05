#!/usr/bin/env groovy
def call(args, dockerFile = './Dockerfile', folder = '.') {
    textWithColor("Building Container")
    DOCKER_BUILD_ARGS = ''
    args.each { arg,value ->
        DOCKER_BUILD_ARGS = "${DOCKER_BUILD_ARGS} --build-arg ${arg}=${value}"
    }
    if(isWindows()) {
        powershell "docker build ${DOCKER_BUILD_ARGS} -t ${CONTAINER_NAME} -f ${dockerFile} ${folder}"
        powershell "docker tag ${CONTAINER_NAME} ${CONTAINER_NAME_REPO}"
        powershell "docker push ${CONTAINER_NAME_REPO}"
    } else {
        sh "docker build ${DOCKER_BUILD_ARGS} -t ${CONTAINER_NAME} -f ${dockerFile} ${folder}"
        sh "docker tag ${CONTAINER_NAME} ${CONTAINER_NAME_REPO}"
        sh "docker push ${CONTAINER_NAME_REPO}"
    }

    env.DOCKER_BUILD_ARGS = DOCKER_BUILD_ARGS
    textWithColor("Finished Building Container")
}
