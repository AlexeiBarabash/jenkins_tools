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
    textWithColor("Git Clone - Cleans")
    // if(isWindows()) {
    //     powershell "git submodule update --init --recursive"
    //     powershell "git submodule foreach 'git checkout ${BRANCH_TO_CLONE}'"
    //     powershell 'git reset --hard'
    //     powershell 'git clean -dfx'
    //     powershell "dir" 
    // } else {
    //     sh "git submodule update --init --recursive || true"
    //     sh "git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'"
    //     sh 'git reset --hard || true'
    //     sh 'git clean -dfx || true'
    //     sh "ls -latr"
    // }
    bashCommand("git submodule update --init --recursive || true")
    bashCommand("git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'")
    bashCommand('git reset --hard || true')
    bashCommand('git clean -dfx || true')
    bashCommand("ls -latr")
    textWithColor("Finish Git Clone - ${BRANCH_TO_CLONE}")
}
