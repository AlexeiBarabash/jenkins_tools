#!/usr/bin/env groovy

def call(filePath, repo = 'https://nexus.codeoasis.com/repository/Files/', user = 'jenkins:zxasqw12') {
    textWithColor("Publish Zip Artifact To Nexus - ${filePath}")
    def fileName = (JOB_NAME + '_' + BUILD_NUMBER + '.zip').replace(" ","_")
    def auth = 'Basic ' + user.bytes.encodeBase64().toString()
    bashCommand("curl --insecure -v --user '${user}' --upload-file ${filePath} ${repo + fileName}")
    textWithColor("Done Publish Zip Artifact To Nexus")

    return repo + fileName
}
