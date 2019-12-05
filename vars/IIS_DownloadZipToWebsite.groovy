#!/usr/bin/env groovy
def call() {
    textWithColor("IIS_DownloadZipToWebsite")
    bashCommand("rm -rf ./Artifact.zip || true")
    downloadFile('./Artifact.zip',env.ArtifactUrl)
    bashCommand("ls -latr")
    unzip dir: env.IIS_FOLDER, zipFile: './Artifact.zip'
    textWithColor("DONE IIS_DownloadZipToWebsite")
}
