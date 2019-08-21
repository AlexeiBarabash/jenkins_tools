#!/usr/bin/env groovy
def call() {
    // if( env.ENV == "qa" ) {
        def publisher = LastChanges.getLastChangesPublisher "LAST_SUCCESSFUL_BUILD", "SIDE", "LINE", true, true, "", "", "", "", ""
            publisher.publishLastChanges()
            def changes = publisher.getLastChanges()
            println(changes.getEscapedDiff())
            for (commit in changes.getCommits()) {
                println(commit)
                def commitInfo = commit.getCommitInfo()
                    println(commitInfo)
                    println(commitInfo.getCommitMessage())
                    println(commit.getChanges())
            }
    // }
    //slackSend(token: env.SLACK_TOKEN, channel: env.SLACK_CH, color: '#00FF00', message: proc.text)
}
