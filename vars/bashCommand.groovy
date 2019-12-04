#!/usr/bin/env groovy
def call(cmd) {
    if(isWindows()) {
        powershell "C:\\\"Program Files\"\\Git\\bin\\bash.exe -c \"${cmd}\""
    } else {
        sh cmd
    }
}
