#!/usr/bin/env groovy
def call() {
    textWithColor("Init")
    if(env.ENV == null || env.ENV == '') {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    
    textWithColor("Config")
    env.CONFIG = readJSON(text: env.CONFIG)
    env.CONFIG = CONFIG["${env.ENV}"]
    writeJSON file: 'myConfig.json', json: env.CONFIG
    sh "cat myConfig.json"
    
    env.CONTAINER_RUN_ARGS = CONFIG.CONTAINER_RUN_ARGS
    env.SERVERS = env.CONFIG.SERVERS
    env.DOCKER_REPO = env.CONFIG.DOCKER_REPO

    env.BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_BRANCH ?: "integration"
    env.GIT_REPO = GIT_URL ?: gitlabSourceRepoURL ?: gitlabSourceRepoSshUrl
    
    wrap([$class: 'BuildUser']) {
        env.BUILDER_NAME = (BUILD_USER == '' || BUILD_USER == null || BUILD_USER == 'SCMTrigger') ? gitlabUserName : BUILD_USER
    }
    
    env.CONTAINER_NAME = "${env.CONTAINER_NAME}-${env.ENV}"
    env.CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : env.BRANCH_TO_CLONE}"
    env.CONTAINER_NAME_REPO = "${CONFIG.DOCKER_REPO}/${env.CONTAINER_NAME}:${env.CONTAINER_VERSION}"
}
