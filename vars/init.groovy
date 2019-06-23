#!/usr/bin/env groovy
def call() {
    if(ENV == null || ENV == '') {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    
    // SERVERS = []
    DOCKER_REPO = config.DOCKER_REPO
    BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_BRANCH ?: "integration"
    // if(ENV == 'integration') {
    //     SERVERS = SERVERS_INTEGRATION
    //     CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_INTEGRATION} "
    //     DOCKER_REPO = DOCKER_REPO_INTEGRATION
    // }
    // else if(ENV == 'qa') {
    //     SERVERS = SERVERS_QA
    //     CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_QA} "
    //     DOCKER_REPO = DOCKER_REPO_QA
    // }
    // else if(ENV == 'stage') {
    //     SERVERS = SERVERS_STAGE
    //     CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_STAGE} "
    //     DOCKER_REPO = DOCKER_REPO_STAGE
    // } else if(ENV == 'production') {
    //     SERVERS = SERVERS_PROD
    //     CONTAINER_RUN_ARGS += " ${CONTAINER_RUN_ARGS_PROD} "
    //     DOCKER_REPO = DOCKER_REPO_PROD
    // }
            
    

    CONTAINER_NAME = "${CONTAINER_NAME}-${ENV}"
    CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : env.BRANCH_TO_CLONE}"
    CONTAINER_NAME_REPO = "${DOCKER_REPO}/${CONTAINER_NAME}:${CONTAINER_VERSION}"

    env.CONTAINER_NAME = CONTAINER_NAME
    env.CONTAINER_VERSION = CONTAINER_VERSION
    env.CONTAINER_NAME_REPO = CONTAINER_NAME_REPO
    env.DOCKER_REPO = DOCKER_REPO
    env.CONTAINER_RUN_ARGS = config.CONTAINER_RUN_ARGS
    env.SERVERS = config.SERVERS
    env.BRANCH_TO_CLONE = BRANCH_TO_CLONE
}
