#!/usr/bin/env groovy
def call() {
    if(ENV == null || ENV == '') {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    SERVERS = []
    DOCKER_REPO = ""

    if(ENV == 'integration') {
        SERVERS = SERVERS_INTEGRATION.split(';')
        CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_INTEGRATION} "
        DOCKER_REPO = DOCKER_REPO_INTEGRATION
    }
    else if(ENV == 'qa') {
        SERVERS = SERVERS_QA.split(';')
        CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_QA} "
        DOCKER_REPO = DOCKER_REPO_QA
    }
    else if(ENV == 'stage') {
        SERVERS = SERVERS_STAGE.split(';')
        CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_STAGE} "
        DOCKER_REPO = DOCKER_REPO_STAGE
    } else if(ENV == 'production') {
        SERVERS = SERVERS_PROD.split(';')
        CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_PROD} "
        DOCKER_REPO = DOCKER_REPO_PROD
    }
            
    CONTAINER_NAME = "${CONTAINER_NAME}-${ENV}"
    CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : BRANCH_TO_CLONE}"
    CONTAINER_NAME_REPO = "${DOCKER_REPO}/${CONTAINER_NAME}:${CONTAINER_VERSION}"
}
