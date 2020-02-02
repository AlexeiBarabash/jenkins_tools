#!/usr/bin/env groovy
def call(checkTriggered = false, ignoreResult = true) {
    try {
        textWithColor("newSlackSendHelper start")
        def success = currentBuild.result != 'FAILURE'
        if(!ignoreResult && currentBuild.result != 'SUCCESS' && currentBuild.result != 'FAILURE')
        {
            textWithColor("dont need to send slack - status = ${currentBuild.result}")
            return
        }
        if(checkTriggered && !needToTrigger()) {
            textWithColor("dont need to send slack")
            return
        }
        if(isEmpty(env.SLACK_CH)) {
            textWithColor("env.SLACK_CH is missing", "red")
            return
        }
        if(isEmpty(env.BRANCH_TO_CLONE)) {
            env.BRANCH_TO_CLONE = 'unknown'
        }
        if(isEmpty(env.BUILDER_NAME)) {
            env.BUILDER_NAME = 'unknown'
        }

        def title = " Job '${JOB_NAME} *[${env.BRANCH_TO_CLONE}]'* By *${env.BUILDER_NAME}*"
        textWithColor("get last commit")
        def text = (success ? "*SUCCESSFUL* -"  :  "*FAILED* - ${env.STAGE_NAME} -") + " ${env.LastCommit} \n ${env.LastCommitWithoutMerges}".replace("\n","\\n")
        text = text.trim().replace("\n","\\n").trim()
        def body = """
        {
            "username": "Jenkins",
            "icon_url": "${success ? "https://i.imgur.com/T0O4r13.png" : "https://i.imgur.com/f2V8vlc.png"}",
            "channel": "${env.SLACK_CH}",
            "attachments": [
                {
                    "color": "${success ? '#00FF00' : '#FF0000'}",
                    "text": "${text}",
                    "footer": "takes ${currentBuild.durationString}",
                    "title": "${title}",
                    "title_link": "${BUILD_URL}"
                }
            ]
        }
        """

        echo body

        def bearerToken = "Bearer ${env.GLOBAL_SLACK_TOKEN}"
        response = httpRequest ( consoleLogResponseBody: true,
            httpMode: 'POST',
            requestBody: body,
            url: "https://slack.com/api/chat.postMessage",
            customHeaders: [
                [name: 'Authorization', value: bearerToken],
                [name: 'Content-Type', value: 'application/json; charset=utf-8']
            ]
        )

        textWithColor("newSlackSendHelper success", "green")
    } catch(Exception ex) {
        if(ex.toString().indexOf("java.io.NotSerializableException") >= 0) {
            textWithColor("newSlackSendHelper success", "green")
        } else {
            textWithColor("newSlackSendHelper Error", "red")
            echo ex.toString()
        }
    }
}
