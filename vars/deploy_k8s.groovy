#!/usr/bin/env groovy
def call() {
    textWithColor("Deploy Container With K8s")
    kubernetesDeploy(
        kubeconfigId: 'int_kubeconfig',
        configs: env.K8S_APPLY_FILES_GLOB,
        enableConfigSubstitution: true
    )
    textWithColor("Finished Deploy Container")
}
