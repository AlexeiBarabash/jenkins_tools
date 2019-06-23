def call() {
    textWithColor("Deploying ${ENV}")
    for(server in SERVERS.split(';')) {
        if((fileExists(env.K8S_APPLY_FILE))) {
            deploy_k8s(env.K8S_APPLY_FILE)
        } else {
            sshDeploy(server)
        }
    }
    textWithColor("Finished Deploying ${ENV}")
}
