#!/usr/bin/env groovy
def call() {
    textWithColor("Deploy Container With K8s")
    if(env.KUBE_CONFIG_ID == "kubeconfig_codeoasis") {
        sh "kubectl config view"
        sh "kubectl get nodes"
        sh "kubectl apply -f " + env.K8S_APPLY_FILES_GLOB
    } else {
        kubernetesDeploy(
            kubeconfigId: env.KUBE_CONFIG_ID,
            configs: env.K8S_APPLY_FILES_GLOB,
            enableConfigSubstitution: true
        )
    }

    textWithColor("Finished Deploy Container")
}
