#!/usr/bin/env groovy
@NonCPS
def call() {
    try {
        textWithColor("getBuildNameFromCommits")
        env.BUILDER_NAME = getLastCommit().author
        textWithColor("getBuildNameFromCommits done - ${env.BUILDER_NAME}", "green")
    } catch(Exception ex) {
        env.BUILDER_NAME = 'unknown'
        textWithColor("getBuildNameFromCommits Error", "red")
        echo ex.toString()
    }
}


