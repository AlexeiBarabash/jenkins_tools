#!/usr/bin/env groovy
def call() {
    // if( env.ENV == "qa" ) {
        textWithColor('git changes log start')
        def messageForWebhook = "git logs: \n";
        def publisher = LastChanges.getLastChangesPublisher "LAST_SUCCESSFUL_BUILD", "SIDE", "LINE", true, true, "", "", "", "", ""
        publisher.publishLastChanges()
        def changes = publisher.getLastChanges()
        // println(changes.getEscapedDiff())
        for (commit in changes.getCommits()) {
            println(commit)
            def commitInfo = commit.getCommitInfo()
            def message = commitInfo.getCommitMessage()
            println(commitInfo)
            println(message)
            messageForWebhook = messageForWebhook + message + "\n"
            // println(commit.getChanges())
        }

        textWithColor('git webhook start')
        println(messageForWebhook)
        def response = httpRequest(
            url: URLEncoder.encode('http://mydaily.codeoasis.com/api/webhooks/gitlogs/?git=' + env.GIT_REPO + "&log=" + messageForWebhook.replace("&"," and ").replace("\n", "    ")),
            // requestBody : '{ "log" : "' + messageForWebhook + '" }',
            // requestBody : '{ "log" : "aa" }',
            httpMode: "POST"
        )


        textWithColor('git webhook end')

        textWithColor('git changes log end')
}
