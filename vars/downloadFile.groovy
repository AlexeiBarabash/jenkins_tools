#!/usr/bin/env groovy
def call(outputFile ,urlPath, basicAuth = 'jenkins:zxasqw12') {
    textWithColor("Download File")
    echo outputFile
    echo urlPath
    bashCommand("curl --insecure -v --user '${basicAuth}' --output ${outputFile} ${urlPath}")
    textWithColor("Done Download File", "green")
}

