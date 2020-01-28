#!/usr/bin/env groovy
def call(outputFile ,urlPath, basicAuth = 'jenkins:zxasqw12') {
    textWithColor("Download File")
    echo outputFile
    echo urlPath
    bashCommand("curl --insecure -v --user '${basicAuth}' --output ${outputFile} --retry 999 --retry-max-time 0 -C ${urlPath}")
    textWithColor("Done Download File", "green")
}

