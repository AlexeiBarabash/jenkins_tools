#!/usr/bin/env groovy
def call() {
    textWithColor("Git Clone - ${BRANCH_TO_CLONE}")
    checkout([$class: 'GitSCM',
        branches: [[name: BRANCH_TO_CLONE]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        gitTool: 'Default',
        submoduleCfg: [],
        userRemoteConfigs: [[url: GIT_REPO]]
    ])
    if(isEmpty(env.IsWindows)) {
        sh "git submodule update --init --recursive || true"
        sh "git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'"
        sh "ls -latr"
    } else {
        powershell "git submodule update --init --recursive || true"
        powershell "git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'"
        powershell "ls -latr"
    }
    
    textWithColor("Finish Git Clone - ${BRANCH_TO_CLONE}")
}
