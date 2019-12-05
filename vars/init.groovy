#!/usr/bin/env groovy
def call(autoDetectEnv = false,autoDetectEnvFirstLetterUpper = false) {
    textWithColor("Init")
    
    try {
        env.BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_BRANCH ?: ""
    } catch(Exception ex) {
        textWithColor("params.TAG_OR_BRANCH or GIT_BRANCH is missing")
        echo ex.toString()
        env.BRANCH_TO_CLONE = ''
    }

    if(autoDetectEnv && !isEmpty(env.BRANCH_TO_CLONE)) {
        if(env.BRANCH_TO_CLONE.toLowerCase() == 'master' || env.BRANCH_TO_CLONE.toLowerCase() == 'origin/master') {
            env.ENV = 'stage'
        } else if(env.BRANCH_TO_CLONE.toLowerCase() == 'qa' || env.BRANCH_TO_CLONE.toLowerCase() == 'origin/qa') {
            env.ENV = 'qa'
        } else if(env.BRANCH_TO_CLONE.toLowerCase() == 'integration' || env.BRANCH_TO_CLONE.toLowerCase() == 'origin/integration') {
            env.ENV = 'integration'
        }
        if(!isEmpty(env.ENV)) {
            if(autoDetectEnvFirstLetterUpper) {
                env.ENV = env.ENV[0].toUpperCase() + env.ENV.substring(1)
            }
            textWithColor("Auto Detect Env, ENV=${env.ENV}")
        }
    }

    if(isEmpty(env.ENV)) {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    ENV = env.ENV

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
        env.IIS_FOLDER = CONFIG.IIS_FOLDER ?: ""

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
        textWithColor("BuildUser Error")
        echo ex.toString()
        getBuildNameFromCommits()
    }

    
    env.CONTAINER_NAME = "${env.CONTAINER_NAME}-${ENV}".replace("_", "-")
    env.CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : env.BRANCH_TO_CLONE}"
    env.CONTAINER_NAME_REPO = "${env.DOCKER_REPO}/${env.CONTAINER_NAME}:${env.CONTAINER_VERSION}"
}
