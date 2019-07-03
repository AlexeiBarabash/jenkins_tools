#!/usr/bin/env groovy
def call() {
    textWithColor("Deploy Container With K8s")
    if(env.KUBE_CONFIG_ID == "kubeconfig_codeoasis") {
        kubernetesDeploy(
            kubeConfig: path:"/root/.kube/config.yaml",
            configs: env.K8S_APPLY_FILES_GLOB,
            enableConfigSubstitution: true
        )
    } else {
        kubernetesDeploy(
            kubeconfigId: env.KUBE_CONFIG_ID,
            configs: env.K8S_APPLY_FILES_GLOB,
            enableConfigSubstitution: true
        )
    }

    textWithColor("Finished Deploy Container")
}
