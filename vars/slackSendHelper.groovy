#!/usr/bin/env groovy
def call(success) {
    message = " Job '${JOB_NAME} *[${env.BRANCH_TO_CLONE}]'* By *${env.BUILDER_NAME} - _${currentBuild.durationString}_* - ${BUILD_URL}";
    message = success ? "*SUCCESSFUL:*" + message :  "*FAILED:*" + message
    color =  success ? '#00FF00' : '#FF0000'
    slackSend(token: SLACK_TOKEN, channel: SLACK_CH, color: color, message: message)
}
