#!/usr/bin/env groovy
def call(applyFilename) {
    textWithColor("Deploy Container With K8s")
    kubernetesDeploy(
        kubeconfigId: 'int_kubeconfig',
        configs: applyFilename,
        enableConfigSubstitution: true
    )
    textWithColor("Finished Deploy Container")
}
