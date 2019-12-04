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
    if(isWindows()) {
        powershell "git submodule update --init --recursive"
        powershell "git submodule foreach 'git checkout ${BRANCH_TO_CLONE}'"
        powershell 'git reset --hard'
        powershell 'git clean -dfx'
        powershell "dir" 
    } else {
        sh "git submodule update --init --recursive || true"
        sh "git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'"
        sh 'git reset --hard || true'
        sh 'git clean -dfx || true'
        sh "ls -latr"
    }
 
    textWithColor("Finish Git Clone - ${BRANCH_TO_CLONE}")
}
