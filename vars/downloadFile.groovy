#!/usr/bin/env groovy
def call(outputFile ,urlPath, basicAuth = 'jenkins:zxasqw12') {
    textWithColor("Download File")
    echo outputFile
    echo urlPath
    def userAndPass = basicAuth.split(':')
    // bashCommand("curl --insecure -v --user '${basicAuth}' --output ${outputFile} --retry 999 --retry-max-time 0 ${urlPath}")
    bashCommand("wget -O ${outputFile} --user ${userAndPass[0]} --password ${userAndPass[1]} --no-check-certificate --retry-connrefused --waitretry=1 --read-timeout=20 --timeout=15 -t 0 ${urlPath}")
    textWithColor("Done Download File", "green")
}

