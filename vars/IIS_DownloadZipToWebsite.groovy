#!/usr/bin/env groovy
def call() {
    bashCommand("rm -rf ./Artifact.zip")
    downloadFile('./Artifact.zip',env.ArtifactUrl)
    bashCommand("ls -latr")
    unzip dir: env.IIS_FOLDER, zipFile: './Artifact.zip'
}
