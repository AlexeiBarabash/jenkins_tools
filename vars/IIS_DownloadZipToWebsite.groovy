#!/usr/bin/env groovy
def call() {
    textWithColor("IIS_DownloadZipToWebsite")
    if(isEmpty(env.IIS_FOLDER)) {
        throw new Exception('env.IIS_FOLDER is must')
    }
    bashCommand("rm -rf ./Artifact.zip || true")
    downloadFile('./Artifact.zip',env.ArtifactUrl)
    bashCommand("ls -latr")
    unzip charset: 'UTF8', dir: env.IIS_FOLDER, zipFile: './Artifact.zip'
    textWithColor("DONE IIS_DownloadZipToWebsite")
}
