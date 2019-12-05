#!/usr/bin/env groovy
def call() {
    textWithColor("IIS_DownloadZipToWebsite")
    if(isEmpty(env.IIS_FOLDER)) {
        throw new Exception('env.IIS_FOLDER is must')
    }
    bashCommand("rm -rf ./Artifact.zip || true")
    downloadFile('./Artifact.zip',env.ArtifactUrl)
    bashCommand("ls -latr")
    //powershell "Expand-Archive ./Artifact.zip -DestinationPath ${env.IIS_FOLDER} -Force"
    unzip dir: env.IIS_FOLDER, zipFile: './Artifact.zip'
    //bashCommand("unzip -o ./Artifact.zip -d ${env.IIS_FOLDER}")
    textWithColor("DONE IIS_DownloadZipToWebsite")
}
