#!/usr/bin/env groovy
def call() {
    textWithColor("Init")
    if(ENV == null || ENV == '') {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    
    textWithColor("Config")
    CONFIG = readJSON(text:CONFIG)
    CONFIG = CONFIG["${ENV}"]
    writeJSON(CONFIG, "myConfig.json")
    sh "cat myConfig.json"
    
    BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_BRANCH ?: "integration"

    CONTAINER_NAME = "${CONTAINER_NAME}-${ENV}"
    CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : env.BRANCH_TO_CLONE}"
    CONTAINER_NAME_REPO = "${CONFIG.DOCKER_REPO}/${CONTAINER_NAME}:${CONTAINER_VERSION}"
    
    wrap([$class: 'BuildUser']) {
        BUILDER_NAME = (BUILD_USER == '' || BUILD_USER == null || BUILD_USER == 'SCMTrigger') ? gitlabUserName : BUILD_USER
    }

    env.BUILDER_NAME = BUILDER_NAME
    env.CONTAINER_NAME = CONTAINER_NAME
    env.CONTAINER_VERSION = CONTAINER_VERSION
    env.CONTAINER_NAME_REPO = CONTAINER_NAME_REPO
    env.DOCKER_REPO = CONFIG.DOCKER_REPO
    env.CONTAINER_RUN_ARGS = CONFIG.CONTAINER_RUN_ARGS
    env.SERVERS = CONFIG.SERVERS
    env.BRANCH_TO_CLONE = BRANCH_TO_CLONE
    env.GIT_REPO = GIT_URL ?: gitlabSourceRepoURL ?: gitlabSourceRepoSshUrl

}
