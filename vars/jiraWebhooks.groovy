#!/usr/bin/env groovy
def call(integrationIsQa = false) {
    try {
        if(!(env.ENV.toLowerCase() == "qa" || (integrationIsQa && env.ENV.toLowerCase() = 'integration'))) {
            return;
        }
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
            url: 'http://mydaily.codeoasis.com/api/webhooks/gitlogs/?log=' + URLEncoder.encode(messageForWebhook.replace("&"," and ").replace("\n", "    ")),
            httpMode: "POST"
        )
        textWithColor('git webhook end')
        textWithColor('git changes log end')
        
    } catch(Exception ex) {
        textWithColor("jiraWebhooks Error")
        echo ex.toString()
    }
}
