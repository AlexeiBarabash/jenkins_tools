#!/usr/bin/env groovy
def call(cmd) {
    def isWindows = false
    try {
        isWindows = isWindows()
    } catch(Exception ex) {
        isWindows = env.isWindows == "true"
    }

    try {
        def res = null
        if(isWindows) {
            cmd = cmd.replace('"','\"')
            cmd = cmd.replace('\\','\\\\')
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
