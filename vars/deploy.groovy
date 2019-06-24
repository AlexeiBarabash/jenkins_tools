def call() {
    textWithColor("Deploying ${ENV}")
    if(env.K8S_APPLY_FILES_GLOB != "" && env.K8S_APPLY_FILES_GLOB != null && (fileExists(env.K8S_APPLY_FILES_GLOB))) {
            deploy_k8s()
    } else {
        echo env.SERVERS
        for(server in env.SERVERS) {
            sshDeploy(server)
        }
    }
    textWithColor("Finished Deploying ${ENV}")
}
