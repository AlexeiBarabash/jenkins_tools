#!/usr/bin/env groovy
def call(cmd) {
    if(isWindows()) {
        powershell (returnStdout: true, script:"C:\\\"Program Files\"\\Git\\bin\\bash.exe -c \"${cmd}\"")
        return
    }
    sh(returnStdout: true, script:cmd)
}
