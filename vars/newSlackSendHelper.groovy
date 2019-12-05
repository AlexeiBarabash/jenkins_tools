#!/usr/bin/env groovy
def call(checkTriggered = true) {
    success = currentBuild.result == 'SUCCESS'
    if(currentBuild.result != 'SUCCESS' && currentBuild.result != 'FAILURE')
    {
        return;
    }
    if(checkTriggered && !needToTrigger()) {
        return;
    }
    try {
        if(isEmpty(env.SLACK_TOKEN)) {
            env.SLACK_TOKEN = 'bsQQ1TYnge9gh1f9qqv4DeHU'
        }
        if(isEmpty(env.BRANCH_TO_CLONE)) {
            env.BRANCH_TO_CLONE = 'unknown'
        }
        if(isEmpty(env.BUILDER_NAME)) {
            env.BUILDER_NAME = 'unknown'
        }
        if(isEmpty(env.SLACK_CH)) {
            textWithColor("env.SLACK_CH is missing")
            return
        }
        def title = " Job '${JOB_NAME} *[${env.BRANCH_TO_CLONE}]'* By *${env.BUILDER_NAME}*"
        def title_link = BUILD_URL;
        
        def message = success ? "*SUCCESSFUL*"  :  "*FAILED*"
        def color =  success ? '#00FF00' : '#FF0000'
        def body = """
        {
            "channel": "${env.SLACK_CH}",
            "attachments": [
                {
                    "color": "${color}",
                    "text": "${message}",
                    "footer": "${currentBuild.durationString}",
                    "title": "${title}",
                    "title_link": "${title_link}"
                }
            ]
        }
        """
        response = httpRequest (consoleLogResponseBody: true,
        contentType: 'APPLICATION_JSON',
        httpMode: 'POST',
        requestBody: body,
        url: "https://codeoasis.slack.com/services/hooks/jenkins-ci?token=" + env.SLACK_TOKEN,
        validResponseCodes: '200')
    } catch(Exception ex) {}
}
