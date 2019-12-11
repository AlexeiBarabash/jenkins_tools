#!/usr/bin/env groovy
def call(checkTriggered = true, ignoreResult = false, slackUrl = 'https://codeoasis.slack.com/services/hooks/jenkins-ci') {
    def success = currentBuild.result == 'SUCCESS'
    if(!ignoreResult && currentBuild.result != 'SUCCESS' && currentBuild.result != 'FAILURE')
    {
        return;
    }
    if(checkTriggered && !needToTrigger()) {
        return;
    }
    if(isEmpty(env.SLACK_TOKEN)) {
        textWithColor("env.SLACK_TOKEN is missing", "red")
        return
    }
    if(isEmpty(env.SLACK_CH)) {
        textWithColor("env.SLACK_CH is missing", "red")
        return
    }
    try {
        if(isEmpty(env.SLACK_TOKEN)) {
            env.SLACK_TOKEN = 'xoxp-2237703800-870635606503-868348607988-818e501f09e769d9eef109d179f43fa0'
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
        textWithColor("newSlackSendHelper success", "green")
    } catch(Exception ex) {
        textWithColor("newSlackSendHelper Error", "red")
        echo ex.toString()
    }
}
