#!/usr/bin/env groovy
def call() {
    // if( env.ENV == "qa" ) {
        textWithColor('git changes log start')
        def messageForWebhook = "";
        def publisher = LastChanges.getLastChangesPublisher "LAST_SUCCESSFUL_BUILD", "SIDE", "LINE", true, true, "", "", "", "", ""
        publisher.publishLastChanges()
        def changes = publisher.getLastChanges()
        // println(changes.getEscapedDiff())
        for (commit in changes.getCommits()) {
            println(commit)
            def commitInfo = commit.getCommitInfo()
            println(commitInfo)
            println(commitInfo.getCommitMessage())
            messageForWebhook = messageForWebhook + "\n" + commitInfo.getCommitMessage()
            // println(commit.getChanges())
        }

        textWithColor('git webhook start')
        println(messageForWebhook)
        def response = httpRequest(
            url: 'http://mydaily.codeoasis.com/api/webhooks/gitlogs/?git=' + GIT_REPO,
            body: '{ "log" : "' + messageForWebhook + '" }',
            httpMode: "POST"
        )


        textWithColor('git webhook end')

        textWithColor('git changes log end')
}
