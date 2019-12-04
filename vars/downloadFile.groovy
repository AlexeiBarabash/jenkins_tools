#!/usr/bin/env groovy

def call(outputFile ,urlPath,isWindows = false, basicAuth = 'jenkins:zxasqw12') {
    textWithColor("Download File")
    // def auth = 'Basic ' + basicAuth.bytes.encodeBase64().toString()
    // httpRequest (consoleLogResponseBody: true,
    //         httpMode: 'GET',
    //         outputFile: outputFile,
    //         url: urlPath,
    //         responseHandle: 'NONE',
    //         customHeaders: [[maskValue: false, name: 'Authorization', value: auth]]
    // )
    def curlCmd = "curl -v --user '${basicAuth}' --output ${outputFile} ${urlPath}"
    if(isWindows) {
        powershell "C:\\\"Program Files\"\\Git\\bin\\bash.exe -c \"${curlCmd}\""
    } else {
        sh curlCmd
    }

    textWithColor("Done Download File")
}
