#!/usr/bin/env groovy
def call(success, useHttpRequest = false) {
    try {
        if(isEmpty(env.SLACK_TOKEN)) {
            env.SLACK_TOKEN = 'bsQQ1TYnge9gh1f9qqv4DeHU'
        }
        message = " Job '${JOB_NAME} *[${env.BRANCH_TO_CLONE}]'* By *${env.BUILDER_NAME} - _${currentBuild.durationString}_* - ${BUILD_URL}";
        message = success ? "*SUCCESSFUL:*" + message :  "*FAILED:*" + message
        color =  success ? '#00FF00' : '#FF0000'
        if(useHttpRequest) {
            response = httpRequest (consoleLogResponseBody: true,
            contentType: 'APPLICATION_JSON',
            httpMode: 'POST',
            requestBody: "{ \"text\" : \"" + message +  "\" }",
            url: "https://codeoasis.slack.com/services/hooks/jenkins-ci/?token=" + env.SLACK_TOKEN,
            validResponseCodes: '200')
            return
        }
        slackSend(token: env.SLACK_TOKEN, channel: env.SLACK_CH, color: color, message: message)
    } catch(Exception ex) {}
}
