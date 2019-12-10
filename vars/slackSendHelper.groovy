#!/usr/bin/env groovy
def call(success, useHttpRequest = true, slackUrl = 'https://codeoasis.slack.com/services/hooks/jenkins-ci') {
    success = currentBuild.result == 'SUCCESS'
    try {
        if(isEmpty(env.SLACK_TOKEN)) {
            textWithColor("env.SLACK_CH is missing")
            return
        }
        if(isEmpty(env.SLACK_CH)) {
            textWithColor("env.SLACK_CH is missing")
            return
        }
        if(isEmpty(env.BRANCH_TO_CLONE)) {
            env.BRANCH_TO_CLONE = 'unknown'
        }
        if(isEmpty(env.BUILDER_NAME)) {
            env.BUILDER_NAME = 'unknown'
        }

        def title = " Job '${JOB_NAME} *[${env.BRANCH_TO_CLONE}]'* By *${env.BUILDER_NAME}*"
        def title_link = BUILD_URL;
        
        def message = success ? "*SUCCESSFUL*"  :  "*FAILED*"
        def color =  success ? '#00FF00' : '#FF0000'
        if(useHttpRequest) {
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
            url: "${slackUrl}?token=" + env.SLACK_TOKEN,
            validResponseCodes: '200')
            return
        }
        message = message + title
        message = message + "*- _${currentBuild.durationString}_*"
        message = message + title_link
        slackSend(token: env.SLACK_TOKEN, channel: env.SLACK_CH, color: color, message: message)
        textWithColor("slackSendHelper success", "green")
    } catch(Exception ex) {
        textWithColor("slackSendHelper Error", "red")
        echo ex.toString()
    }
}
