#!/usr/bin/env groovy
def call() {
    if(ENV == null || ENV == '') {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    
    // SERVERS = []
    textWithColor("Config")
    echo config
    config = readJSON(text:config)
    BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_BRANCH ?: "integration"

    CONTAINER_NAME = "${CONTAINER_NAME}-${ENV}"
    CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : env.BRANCH_TO_CLONE}"
    CONTAINER_NAME_REPO = "${config.DOCKER_REPO}/${CONTAINER_NAME}:${CONTAINER_VERSION}"

    env.CONTAINER_NAME = CONTAINER_NAME
    env.CONTAINER_VERSION = CONTAINER_VERSION
    env.CONTAINER_NAME_REPO = CONTAINER_NAME_REPO
    env.DOCKER_REPO = config.DOCKER_REPO
    env.CONTAINER_RUN_ARGS = config.CONTAINER_RUN_ARGS
    env.SERVERS = config.SERVERS
    env.BRANCH_TO_CLONE = BRANCH_TO_CLONE
}
