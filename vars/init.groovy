#!/usr/bin/env groovy
def call() {
    textWithColor("Init")
    if(isEmpty(env.ENV)) {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    ENV = env.ENV

    try {
        env.BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_BRANCH ?: "integration"
    } catch(Exception ex) {
        textWithColor("params.TAG_OR_BRANCH or GIT_BRANCH is missing")
        echo ex.toString()
        env.BRANCH_TO_CLONE = 'integration'
    }

    try {
        env.GIT_REPO = GIT_URL ?: gitlabSourceRepoURL ?: gitlabSourceRepoSshUrl
    } catch(Exception ex) {
        textWithColor("params.TAG_OR_BRANCH or GIT_BRANCH is missing")
        echo ex.toString()
    }
   
    textWithColor("Config ${ENV}")
    try {
        CONFIG = env.CONFIG ?: ""
        CONFIG = readJSON(text: CONFIG)
        CONFIG = CONFIG["${ENV}"]

        env.CONTAINER_RUN_ARGS = CONFIG.CONTAINER_RUN_ARGS ?: ""
        env.SERVERS = CONFIG.SERVERS ?: ""
        env.DOCKER_REPO = CONFIG.DOCKER_REPO ?: ""
        env.K8S_APPLY_FILES_GLOB = CONFIG.K8S_APPLY_FILES_GLOB ?: ""
        env.KUBE_CONFIG_ID = CONFIG.KUBE_CONFIG_ID ?: ""
        env.agentLabel = CONFIG.agentLabel ?: ""
        env.isWindows = CONFIG.isWindows ?: "false"

        writeJSON file: './myConfig.json', json: CONFIG
        bashCommand("cat myConfig.json")
    } catch(Exception ex) {
        textWithColor("Config Error")
        echo ex.toString()
    }
 
    try {
        wrap([$class: 'BuildUser']) {
            env.BUILDER_NAME = (BUILD_USER == '' || BUILD_USER == null || BUILD_USER == 'SCMTrigger') ? gitlabUserName : BUILD_USER
        }
    } catch(Exception ex) {
        env.BUILDER_NAME = 'unknown'
    }

    
    env.CONTAINER_NAME = "${env.CONTAINER_NAME}-${ENV}".replace("_", "-")
    env.CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : env.BRANCH_TO_CLONE}"
    env.CONTAINER_NAME_REPO = "${env.DOCKER_REPO}/${env.CONTAINER_NAME}:${env.CONTAINER_VERSION}"
}
