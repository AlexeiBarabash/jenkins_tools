#!/usr/bin/env groovy
def call() {
    textWithColor("Deploy Container With K8s")
    if(env.KUBE_CONFIG_ID == "kubeconfig_codeoasis") {
    sh """
    echo '
        export KUBCONFIG="/var/lib/jenkins/k8sConfig/config.yaml"
        ls  /var/lib/jenkins/k8sConfig/config.yaml
        kubectl --kubeconfig="/var/lib/jenkins/k8sConfig/config.yaml" config view
        kubectl --kubeconfig="/var/lib/jenkins/k8sConfig/config.yaml" get nodes
        kubectl --kubeconfig="/var/lib/jenkins/k8sConfig/config.yaml" apply -f ${env.K8S_APPLY_FILES_GLOB}
        ' > ./script.sh
    """
    sh "chmod 777 ./script.sh"
    sh "./script.sh"

    } else {
        kubernetesDeploy(
            kubeconfigId: env.KUBE_CONFIG_ID,
            configs: env.K8S_APPLY_FILES_GLOB,
            enableConfigSubstitution: true
        )
    }

    textWithColor("Finished Deploy Container")
}
