#!/usr/bin/env groovy
// env.K8S_APPLY_FILES_GLOB = ./k8s/integration.yaml
// env.KUBE_CONFIG_ID = "k8s_config_secret_id"
def call() {
    textWithColor("Deploy Container With K8s")
    kubernetesDeploy(
        kubeconfigId: env.KUBE_CONFIG_ID,
        configs: env.K8S_APPLY_FILES_GLOB,
        enableConfigSubstitution: true
    )
    textWithColor("Finished Deploy Container", "green")
}
