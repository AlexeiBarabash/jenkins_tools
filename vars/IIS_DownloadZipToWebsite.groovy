#!/usr/bin/env groovy
def call(encryptWebconfig = true) {
    textWithColor("IIS_DownloadZipToWebsite")
    if(isEmpty(env.IIS_FOLDER)) {
        textWithColor("env.IIS_FOLDER is must", "red")
        throw new Exception('env.IIS_FOLDER is must')
    }
    bashCommand("rm -rf ./Artifact.zip || true")
    downloadFile('./Artifact.zip',env.ArtifactUrl)
    bashCommand("ls -latr")
    textWithColor("Start Unzipping")
    powershell "\$progressPreference = 'Continue';Expand-Archive ./Artifact.zip -DestinationPath ${env.IIS_FOLDER} -Force"
    if(encryptWebconfig) {
        textWithColor("encrypt webconfig")
        powershell 'C:\\Windows\\Microsoft.NET\\Framework\\v4.0.30319\\ASPNET_REGIIS.exe -pdf connectionStrings" ' + env.IIS_FOLDER.replace('/','\\')
        textWithColor("done encrypt webconfig")
    }
    textWithColor("DONE IIS_DownloadZipToWebsite", "green")
}
