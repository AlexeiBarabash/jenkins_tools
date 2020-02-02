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
        env.SLACK_TOKEN = 'xoxp-2237703800-870635606503-868348607988-818e501f09e769d9eef109d179f43fa0'
        if(isEmpty(env.BRANCH_TO_CLONE)) {
            env.BRANCH_TO_CLONE = 'unknown'
        }
        if(isEmpty(env.BUILDER_NAME)) {
            env.BUILDER_NAME = 'unknown'
        }
        
        def title = " Job '${JOB_NAME} *[${env.BRANCH_TO_CLONE}]'* By *${env.BUILDER_NAME}*"
        def title_link = BUILD_URL
        textWithColor("get last commit")
        def text = (success ? "*SUCCESSFUL* -"  :  "*FAILED* - ${env.STAGE_NAME} -") + " ${env.LastCommit} \n ${env.LastCommitWithoutMerges}".replace("\n","\\n")
        def color =  success ? '#00FF00' : '#FF0000'
        def jsonQuote = "\\\""
        def attachments = "[{"
        attachments += "${jsonQuote}color${jsonQuote}:${jsonQuote}${color}${jsonQuote},"
        attachments += "${jsonQuote}text${jsonQuote}:${jsonQuote}${text}${jsonQuote},"
        attachments += "${jsonQuote}footer${jsonQuote}:${jsonQuote}${currentBuild.durationString}${jsonQuote},"
        attachments += "${jsonQuote}title${jsonQuote}:${jsonQuote}${title}${jsonQuote},"
        attachments += "${jsonQuote}title_link${jsonQuote}:${jsonQuote}${title_link}${jsonQuote}"
        attachments += "}]"
        def image = success ? "https://i.imgur.com/T0O4r13.png" : "https://i.imgur.com/f2V8vlc.png"
        textWithColor("curl script")
        def scriptFile = "./script.sh"
        def script = """
            curl \"https://slack.com/api/chat.postMessage\" \\
            -d username=\"Jenkins\" \\
            -d icon_url=\"${image}\" \\
            -d token=\"${env.SLACK_TOKEN}\" \\
            -d channel=\"${env.SLACK_CH}\" \\
            -d attachments=\"${attachments}\"
        """
        echo script
        bashCommand(" echo '${script}'  > ${scriptFile} ")
        bashCommand("ls -atr")
        bashCommand("chmod 777 " + scriptFile)
        bashCommand(scriptFile)
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
