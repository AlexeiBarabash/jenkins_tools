#!/usr/bin/env groovy
def call(checkTriggered = false, ignoreResult = false) {
    textWithColor("newSlackSendHelper start")
    def success = currentBuild.result == 'SUCCESS'
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
    env.SLACK_TOKEN = 'xoxp-2237703800-870635606503-868348607988-818e501f09e769d9eef109d179f43fa0'
    try {
        if(isEmpty(env.BRANCH_TO_CLONE)) {
            env.BRANCH_TO_CLONE = 'unknown'
        }
        if(isEmpty(env.BUILDER_NAME)) {
            env.BUILDER_NAME = 'unknown'
        }
        
        def title = " Job '${JOB_NAME} *[${env.BRANCH_TO_CLONE}]'* By *${env.BUILDER_NAME}*"
        def title_link = BUILD_URL;
        textWithColor("get last commit")
        def lastCommit = getLastCommit()
        def lastCommitMsg = lastCommit == null  ? "" : lastCommit.msg
        def message = (success ? "*SUCCESSFUL* -"  :  "*FAILED* - ${env.STAGE_NAME} -") + " ${lastCommitMsg}"

        def color =  success ? '#00FF00' : '#FF0000'
        def bashUrl = "https://slack.com/api/chat.postMessage"
        def reqUrl =  "?token=" + env.SLACK_TOKEN
        reqUrl = reqUrl + "&channel=${env.SLACK_CH}"
        reqUrl = reqUrl + "&attachments=" + URLEncoder.encode("[ { \"color\": \"${color}\", \"text\": \"${message}\", \"footer\": \"${currentBuild.durationString}\", \"title\": \"${title}\", \"title_link\": \"${title_link}\" } ]")
        reqUrl = reqUrl + "&username=Jenkins"
        reqUrl = reqUrl + "&icon_url=https://i.imgur.com/T0O4r13.png"
        reqUrl = bashUrl + reqUrl
        httpRequest (url: reqUrl)
        
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
