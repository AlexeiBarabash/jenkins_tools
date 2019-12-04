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
    bashCommand("git submodule update --init --recursive || true")
    bashCommand("git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'")
    bashCommand('git reset --hard || true')
    bashCommand('git clean -dfx || true')
    bashCommand("ls -latr")   
    textWithColor("Finish Git Clone - ${BRANCH_TO_CLONE}")
}
