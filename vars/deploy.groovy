def call() {
    textWithColor("Deploying ${ENV}")
    if((fileExists(env.K8S_APPLY_FILES_GLOB))) {
            deploy_k8s()
    } else {
        for(server in SERVERS) {
            sshDeploy(server)
        }
    }
    textWithColor("Finished Deploying ${ENV}")
}
