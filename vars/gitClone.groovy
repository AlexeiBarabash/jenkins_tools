#!/usr/bin/env groovy
def call() {
    textWithColor("Git Clone - ${BRANCH_TO_CLONE}")
    GIT_REPO = GIT_URL ?: gitlabSourceRepoURL ?: gitlabSourceRepoSshUrl
    GIT_DEFUALT_BRANCH = GIT_BRANCH ?: GIT_DEFUALT_BRANCH
    BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_DEFUALT_BRANCH
    checkout([$class: 'GitSCM',
        branches: [[name: BRANCH_TO_CLONE]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        gitTool: 'Default',
        submoduleCfg: [],
        userRemoteConfigs: [[url: GIT_REPO]]
    ])
    sh "git submodule update --init --recursive || true"
    sh "git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'"
    sh "ls -latr"
    textWithColor("Finish Git Clone - ${BRANCH_TO_CLONE}")
}
