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
    writeJSON file: 'myConfig.json', json: CONFIG
    sh "cat myConfig.json"
    
    env.CONTAINER_RUN_ARGS = CONFIG.CONTAINER_RUN_ARGS
    env.SERVERS = CONFIG.SERVERS
    env.DOCKER_REPO = CONFIG.DOCKER_REPO

    env.BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_BRANCH ?: "integration"
    wrap([$class: 'BuildUser']) {
        env.BUILDER_NAME = (BUILD_USER == '' || BUILD_USER == null || BUILD_USER == 'SCMTrigger') ? gitlabUserName : BUILD_USER
    }
    env.GIT_REPO = GIT_URL ?: gitlabSourceRepoURL ?: gitlabSourceRepoSshUrl

    env.CONTAINER_NAME = "${env.CONTAINER_NAME}-${ENV}"
    env.CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : env.BRANCH_TO_CLONE}"
    env.CONTAINER_NAME_REPO = "${CONFIG.DOCKER_REPO}/${env.CONTAINER_NAME}:${env.CONTAINER_VERSION}"
}
