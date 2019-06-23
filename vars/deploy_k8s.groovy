#!/usr/bin/env groovy
def call() {
    kubernetesDeploy(
        kubeconfigId: 'int_kubeconfig',
        configs: "k8s/${params.MS_PROJECT_NAME}.yaml" ,
        enableConfigSubstitution: true
    )
}