#!/usr/bin/env groovy
// BRANCH_TO_CLONE came from init func
def call(cleanCache = true, credentialsId = null) {
    textWithColor("Git Clone - ${BRANCH_TO_CLONE}")
    checkout([$class: 'GitSCM',
        branches: [[name: BRANCH_TO_CLONE]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        gitTool: 'Default',
        submoduleCfg: [],
        userRemoteConfigs: [[url: GIT_REPO, credentialsId: credentialsId]]
    ])
    textWithColor("Git Clone - Cleans")
    bashCommand('git tag -d $(git tag -l)')
    bashCommand("git submodule foreach 'git tag -d \$(git tag -l)'")
    bashCommand('git reset --hard')
    if(cleanCache) {
        bashCommand('git clean -dfx || true')
    }
    textWithColor("Git Clone - Fetch & Pull")
    bashCommand("git submodule update --init --recursive || true")
    bashCommand("git submodule foreach 'git fetch --all || true'")
    bashCommand("git submodule foreach 'git checkout ${BRANCH_TO_CLONE} -f || true'")
    bashCommand("git submodule foreach 'git pull -f || true'")
    bashCommand("ls -latr")
    env.LastCommitMessage = bashCommand("git log -1 --format=%s").replace("\n", "")
    env.LastCommitHash = bashCommand("git log -1 --format=%h").replace("\n", "")
    env.LastCommitUser = bashCommand("git log -1 --format=%cn").replace("\n", "")
    env.LastCommitDate = bashCommand("git log -1 --format=%ai").replace("\n", "")
    env.LastCommit = "${env.LastCommitHash} - ${env.LastCommitDate} - ${env.LastCommitUser} - ${env.LastCommitMessage}".replace("\n", "")

    env.LastCommitWithoutMergesMessage = bashCommand("git log -1 --format=%s --no-merges").replace("\n", "")
    env.LastCommitWithoutMergesHash = bashCommand("git log -1 --format=%h --no-merges").replace("\n", "")
    env.LastCommitWithoutMergesUser = bashCommand("git log -1 --format=%cn --no-merges").replace("\n", "")
    env.LastCommitWithoutMergesDate = bashCommand("git log -1 --format=%ai --no-merges").replace("\n", "")
    env.LastCommitWithoutMerges = "${env.LastCommitWithoutMergesHash} - ${env.LastCommitWithoutMergesDate} - ${env.LastCommitWithoutMergesUser} - ${env.LastCommitWithoutMergesMessage}".replace("\n", "")

    if(env.LastCommitMessage.indexOf("Merge") < 0) {
        env.LastCommitWithoutMergesMessage = ""
        env.LastCommitWithoutMergesHash = ""
        env.LastCommitWithoutMergesUser = ""
        env.LastCommitWithoutMergesDate = ""
        env.LastCommitWithoutMerges = ""
    }

    textWithColor("Finish Git Clone - ${BRANCH_TO_CLONE}", "green")
}
