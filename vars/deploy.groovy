def call() {
    textWithColor("Deploying ${ENV}")
    for(server in SERVERS.split(';')) {
        if((fileExists("k8s/${ENV}/${env.CONTAINER_NAME}.yaml"))) {
            deploy_k8s("k8s/${ENV}/${env.CONTAINER_NAME}.yaml")
        } else {
            sshDeploy(server)
        }
    }
    textWithColor("Finished Deploying ${ENV}")
}
