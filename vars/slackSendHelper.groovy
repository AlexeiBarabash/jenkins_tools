#!/usr/bin/env groovy
def call(success) {
    message = " Job '${JOB_NAME} *[${BRANCH_TO_CLONE}]'* By *${BUILDER_NAME} - _${currentBuild.durationString}_* - ${BUILD_URL}";
    message = success ? "*SUCCESSFUL:*" + message :  "*FAILED:*" + message
    color =  success ? '#00FF00' : '#FF0000'
    slackSend(token: SLACK_TOKEN, channel: SLACK_CH, color: color, message: message)
}