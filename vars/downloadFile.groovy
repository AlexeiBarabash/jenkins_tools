#!/usr/bin/env groovy

def call(outputFile ,urlPath, basicAuth = 'jenkins:zxasqw12') {
    textWithColor("Download File")
    // def auth = 'Basic ' + basicAuth.bytes.encodeBase64().toString()
    // httpRequest (consoleLogResponseBody: true,
    //         httpMode: 'GET',
    //         outputFile: outputFile,
    //         url: urlPath,
    //         responseHandle: 'NONE',
    //         customHeaders: [[maskValue: false, name: 'Authorization', value: auth]]
    // )
    bashCommand("curl -v --user '${basicAuth}' --output ${outputFile} ${urlPath}")
    textWithColor("Done Download File")
}

