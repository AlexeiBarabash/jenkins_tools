def textWithColor(text) {
    echo "\033[44m --------------------------------------- \033[0m"
    echo "\033[44m ${text} \033[0m"
    echo "\033[44m --------------------------------------- \033[0m"
}

def init() {
    if(ENV == null || ENV == '') {
        textWithColor('ENV param is must')
        throw new Exception('ENV param is must')
    }
    if(ENV == 'integration') {
         DOCKER_REPO = "registry.codeoasis.com:8082"
    }
    GIT_REPO = GIT_URL ?: gitlabSourceRepoURL ?: gitlabSourceRepoSshUrl
    GIT_DEFUALT_BRANCH = GIT_BRANCH ?: GIT_DEFUALT_BRANCH
    BRANCH_TO_CLONE = params.TAG_OR_BRANCH ?: GIT_DEFUALT_BRANCH
    CONTAINER_NAME = "${CONTAINER_NAME}-${ENV}"
    CONTAINER_VERSION = "${(params.TAG_OR_BRANCH == null) ? BUILD_NUMBER : BRANCH_TO_CLONE}"
    CONTAINER_NAME_REPO = "${DOCKER_REPO}/${CONTAINER_NAME}:${CONTAINER_VERSION}"
    wrap([$class: 'BuildUser']) {
        BUILDER_NAME = (BUILD_USER == '' || BUILD_USER == null || BUILD_USER == 'SCMTrigger') ? gitlabUserName : BUILD_USER
    }
    SERVERS = []
    try {
        if(ENV == 'production') {
            SERVERS = SERVERS_PROD.split(';')
            CONTAINER_RUN_ARGS += " " + (CONTAINER_RUN_ARGS_PROD ?: '') + " "
        } else if(ENV == 'stage') {
            SERVERS = SERVERS_STAGE.split(';')
            CONTAINER_RUN_ARGS += " " + (CONTAINER_RUN_ARGS_STAGE ?: '') + " "
        } else if(ENV == 'integration') {
            SERVERS = SERVERS_INTEGRATION.split(';')
            CONTAINER_RUN_ARGS += " " + (CONTAINER_RUN_ARGS_INTEGRATION ?: '') + " "
        }
      } catch(Exception ex) {}

    env.SERVERS = SERVERS
    env.GIT_REPO = GIT_REPO
    env.CONTAINER_NAME_REPO = CONTAINER_NAME_REPO
    env.CONTAINER_NAME = CONTAINER_NAME
    env.CONTAINER_VERSION = CONTAINER_VERSION
    env.ENV = ENV
    env.BUILDER_NAME = BUILDER_NAME
    env.BRANCH_TO_CLONE = BRANCH_TO_CLONE
}

def gitClone() {
    textWithColor("Git Clone - ${BRANCH_TO_CLONE}")
    checkout([$class: 'GitSCM',
        branches: [
            [name: BRANCH_TO_CLONE]
        ],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        gitTool: 'Default',
        submoduleCfg: [],
        userRemoteConfigs: [
            [url: GIT_REPO]
        ]
    ])
    sh "git submodule update --init --recursive || true"
    sh "git submodule foreach 'git checkout ${BRANCH_TO_CLONE} || true'"
    sh "ls -latr"
    textWithColor("Finish Git Clone - ${BRANCH_TO_CLONE}")
}

def buildContainer(args) {
    textWithColor("Building Container")
    DOCKER_BUILD_ARGS = ''
    args.each { arg,value ->
        DOCKER_BUILD_ARGS = "${DOCKER_BUILD_ARGS} --build-arg ${arg}=${value}"
    }
    sh "docker build ${DOCKER_BUILD_ARGS} -t ${CONTAINER_NAME} ."
    sh "docker tag ${CONTAINER_NAME} ${CONTAINER_NAME_REPO}"
    sh "docker push ${CONTAINER_NAME_REPO}"
    env.DOCKER_BUILD_ARGS = DOCKER_BUILD_ARGS
    textWithColor("Finished Building Container")
}

def sshDeploy(sshServer, sshUser = 'deploy') {
    textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
    scriptFile = "./script.sh"
    sh """
        echo '
        docker pull ${CONTAINER_NAME_REPO} || exit 1
        docker stop ${CONTAINER_NAME} || true
        docker rm ${CONTAINER_NAME} || true
        echo 'docker run command'
        docker run ${CONTAINER_RUN_ARGS} --name ${CONTAINER_NAME} ${CONTAINER_NAME_REPO} || exit 1
        docker ps -a
        ' > ${scriptFile}
    """
    sh "ssh ${sshUser}@${sshServer} bash -s < ${scriptFile}"
    textWithColor("Finished Deploy Container")
}

def deploy_k8s() {
    kubernetesDeploy(
        kubeconfigId: 'int_kubeconfig',
        configs: "k8s/${params.MS_PROJECT_NAME}.yaml" ,
        enableConfigSubstitution: true
    )
}

def slackSendHelper(success) {
    message = " Job '${JOB_NAME} *[${BRANCH_TO_CLONE}]'* By *${BUILDER_NAME} - _${currentBuild.durationString}_* - ${BUILD_URL}";
    message = success ? "*SUCCESSFUL:*" + message :  "*FAILED:*" + message
    color =  success ? '#00FF00' : '#FF0000'
    slackSend(token: SLACK_TOKEN, channel: SLACK_CH, color: color, message: message)
}

def printEnv() {
    textWithColor("ENV")
    sh "printenv"
}