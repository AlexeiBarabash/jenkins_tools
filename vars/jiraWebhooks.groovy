#!/usr/bin/env groovy
def call(integrationIsQa = false, postLogUrl = 'http://mydaily.codeoasis.com/api/webhooks/gitlogs/?log=') {
    try {
        if(!(env.ENV.toLowerCase() == "qa" || (integrationIsQa && env.ENV.toLowerCase() == 'integration'))) {
            return
        }
        if(currentBuild.result != 'SUCCESS')
        {
            textWithColor("dont need to webhook - status = ${currentBuild.result}")
            return
        }
        textWithColor('jiraWebhooks')
        textWithColor('git changes log start')
        def logRows = currentBuild.changeSets.size()
        logRows = logRows < 10 ? 10 : logRows*2
        bashCommand("git log -${logRows} --pretty=format:%s > gitlog.txt")
        def gitlog = bashCommand('cat gitlog.txt')
        def messageForWebhook = "git logs: \n" + gitlog
        textWithColor('git webhook start')
        println(messageForWebhook)
        def response = httpRequest(
            url: postLogUrl + URLEncoder.encode(messageForWebhook.replace("&"," and ").replace("\n", "    ")),
            httpMode: "POST"
        )
        textWithColor('git webhook end')
        textWithColor('git changes log end', "green")
    } catch(Exception ex) {
        textWithColor("jiraWebhooks Error", "red")
        echo ex.toString()
    }
}
