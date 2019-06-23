#!/usr/bin/env groovy

def call() {
    if(ENV == null || ENV == '') {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    if(ENV == 'integration') {
         DOCKER_REPO = "registry.codeoasis.com:8082"
    }
    GIT_REPO = GIT_URL ?: gitlabSourceRepoURL ?: gitlabSourceRepoSshUrl
    GIT_DEFUALT_BRANCH = GIT_BRANCH ?: GIT_DEFUALT_BRANCH
    BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_DEFUALT_BRANCH
    CONTAINER_NAME = "${CONTAINER_NAME}-${ENV}"
    CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : BRANCH_TO_CLONE}"
    CONTAINER_NAME_REPO = "${DOCKER_REPO}/${CONTAINER_NAME}:${CONTAINER_VERSION}"
    wrap([$class: 'BuildUser']) {
        BUILDER_NAME = (BUILD_USER == '' || BUILD_USER == null || BUILD_USER == 'SCMTrigger') ? gitlabUserName : BUILD_USER
    }
    SERVERS = []
    try {
        if(ENV == 'production') {
            SERVERS = SERVERS_PROD.split(';')
            CONTAINER_RUN_ARGS += " " + (CONTAINER_RUN_ARGS_PROD ?: '') + " "
        } else if(ENV == 'stage') {
            SERVERS = SERVERS_STAGE.split(';')
            CONTAINER_RUN_ARGS += " " + (CONTAINER_RUN_ARGS_STAGE ?: '') + " "
        } else if(ENV == 'integration') {
            SERVERS = SERVERS_INTEGRATION.split(';')
            CONTAINER_RUN_ARGS += " " + (CONTAINER_RUN_ARGS_INTEGRATION ?: '') + " "
        }
      } catch(Exception ex) {}

    env.SERVERS = SERVERS
    env.GIT_REPO = GIT_REPO
    env.CONTAINER_NAME_REPO = CONTAINER_NAME_REPO
    env.CONTAINER_NAME = CONTAINER_NAME
    env.CONTAINER_VERSION = CONTAINER_VERSION
    env.ENV = ENV
    env.BUILDER_NAME = BUILDER_NAME
    env.BRANCH_TO_CLONE = BRANCH_TO_CLONE
}