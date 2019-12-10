#!/usr/bin/env groovy
import hudson.model.*
def call(integrationIsQa = false) {
    try {
        if(!(env.ENV.toLowerCase() == "qa" || (integrationIsQa && env.ENV.toLowerCase() == 'integration'))) {
            return;
        }
        textWithColor('jiraWebhooks')
        textWithColor('git changes log start')
        def messageForWebhook = "git logs: \n";
        def changeLogSets = currentBuild.changeSets
        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                messageForWebhook = "${messageForWebhook} ${entry.msg} + \n"
            }
        }
        textWithColor('git webhook start')
        println(messageForWebhook)
        def response = httpRequest(
            url: 'http://mydaily.codeoasis.com/api/webhooks/gitlogs/?log=' + URLEncoder.encode(messageForWebhook.replace("&"," and ").replace("\n", "    ")),
            httpMode: "POST"
        )
        textWithColor('git webhook end')
        textWithColor('git changes log end', "green")
        
    } catch(Exception ex) {
        textWithColor("jiraWebhooks Error", "red")
        echo ex.toString()
    }
}
