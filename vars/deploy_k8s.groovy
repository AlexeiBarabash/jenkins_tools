#!/usr/bin/env groovy
def call() {
    textWithColor("Deploy Container With K8s")
    if(env.KUBE_CONFIG_ID == "kubeconfig_codeoasis") {
    sh """
    echo '
        export KUBCONFIG=" /var/lib/jenkins/k8sConfig/config.yaml"
        ls  /var/lib/jenkins/k8sConfig/config.yaml
        echo $KUBCONFIG
        kubectl config view
        kubectl get nodes
        kubectl apply -f ${env.K8S_APPLY_FILES_GLOB}
        ' > ./script.sh
    """

    } else {
        kubernetesDeploy(
            kubeconfigId: env.KUBE_CONFIG_ID,
            configs: env.K8S_APPLY_FILES_GLOB,
            enableConfigSubstitution: true
        )
    }

    textWithColor("Finished Deploy Container")
}
