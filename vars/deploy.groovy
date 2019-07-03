def call() {
    textWithColor("Deploying ${ENV}")
    if(env.K8S_APPLY_FILES_GLOB != "" && env.K8S_APPLY_FILES_GLOB != null) {
            deploy_k8s()
    } else {
        for(server in (readJSON(text: env.SERVERS))) {
            sshDeploy(server)
        }
    }
    textWithColor("Finished Deploying ${ENV}")
}
