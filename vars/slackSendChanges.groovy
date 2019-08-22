#!/usr/bin/env groovy
def call() {
    // if( env.ENV == "qa" ) {
        // println('changes start')
        def publisher = LastChanges.getLastChangesPublisher "LAST_SUCCESSFUL_BUILD", "SIDE", "LINE", true, true, "", "", "", "", ""
            publisher.publishLastChanges()
            def changes = publisher.getLastChanges()
            println(changes.getEscapedDiff())
            for (commit in changes.getCommits()) {
                println(commit)
                def commitInfo = commit.getCommitInfo()
                    println(commitInfo)
                    println(commitInfo.getCommitMessage())
                    println(commit.getChanges())
            }
        // print('changes end')
    // }
    //slackSend(token: env.SLACK_TOKEN, channel: env.SLACK_CH, color: '#00FF00', message: proc.text)



    // def publisher = LastChanges.getLastChangesPublisher null, "SIDE", "LINE", true, true, "", "", "", "", ""
    // publisher.publishLastChanges()
    // def diff = publisher.getDiff()
    // writeFile file: 'build.diff', text: diff
    textWithColor(diff)
    // emailext (
    //     subject: "Jenkins - changes of ${env.JOB_NAME} #${env.BUILD_NUMBER}",
    //     attachmentsPattern: '**/*.diff',
    //     mimeType: 'text/html',
    //     body: """<p>See attached diff of <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b>.</p>
    //     <p>Check build changes on Jenkins <b><a href="${env.BUILD_URL}/last-changes">here</a></b>.</p>""",
    //     to: "YOUR-EMAIL@gmail.com"
    // )
}
