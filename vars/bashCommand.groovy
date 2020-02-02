#!/usr/bin/env groovy
def call(cmd) {
    try {
        def res = null
        if(isWindows()) {
            res = powershell (returnStdout: true, script:"C:\\\"Program Files\"\\Git\\bin\\bash.exe -c \"${cmd}\"")
        } else {
            res = sh(returnStdout: true, script:cmd)
        }
        echo res
        return res
    } catch(Exception ex) {
        textWithColor("bashCommand Error", "red")
        echo ex.toString()
        throw new Exception("bashCommand error")
    }
}
