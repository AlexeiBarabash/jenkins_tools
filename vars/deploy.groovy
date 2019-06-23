def call() {
    textWithColor("Deploying ${ENV}")
    for(server in SERVERS) {
        if((fileExists(env.K8S_APPLY_FILES_GLOB))) {
            deploy_k8s()
        } else {
            sshDeploy(server)
        }
    }
    textWithColor("Finished Deploying ${ENV}")
}
