#!/usr/bin/env groovy

def call(filePath,isWindows = false, repo = 'http://registry.codeoasis.com:8081/repository/Files/', user = 'jenkins:zxasqw12') {
    textWithColor("Publish Zip Artifact To Nexus - ${filePath}")
    def fileName = JOB_NAME + '_' + BUILD_NUMBER + '.zip'
    def auth = 'Basic ' + user.bytes.encodeBase64().toString()
    def curlCmd = "curl -v --user '${user}' --upload-file ${filePath} ${repo + fileName}"
    if(isWindows) {
        powershell "C:\\\"Program Files\"\\Git\\bin\\bash.exe -c \"${curlCmd}\""
    } else {
        sh curlCmd
    }
    textWithColor("Done Publish Zip Artifact To Nexus")

    return repo + fileName
}
