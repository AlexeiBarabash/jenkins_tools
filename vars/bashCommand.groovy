#!/usr/bin/env groovy
def call(cmd) {
    def res = null
    if(isWindows()) {
        res = powershell (returnStdout: true, script:"C:\\\"Program Files\"\\Git\\bin\\bash.exe -c \"${cmd}\"")
    } else {
        res = sh(returnStdout: true, script:cmd)
    }
    echo res
    return res
}
