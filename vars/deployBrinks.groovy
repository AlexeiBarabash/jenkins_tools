def call(sshUser = 'deploy') {
    for(sshServer in (readJSON(text: env.SERVERS))) {
        textWithColor("Deploy Container To - ${sshUser}@${sshServer}")
        def scriptFile = "./script.sh"
        sh """
        echo '
        mkdir -p ./tempDir
        cd ./tempDir
        git clone ${GIT_URL} .
        git reset --hard
        git fetch --all
        git checkout ${env.BRANCH_TO_CLONE}
        git pull
        git reset --hard
        git rm -r --cached .

        docker build -t ${env.CONTAINER_NAME} .
        docker stop ${env.CONTAINER_NAME} || true
        docker rm ${env.CONTAINER_NAME} || true
        echo 'docker run command'
        docker run ${env.CONTAINER_RUN_ARGS} --name ${env.CONTAINER_NAME} ${env.CONTAINER_NAME} || exit 1
        docker ps -a
        rm -rf ./tempDir || true
            ' > ${scriptFile}
        """
        sh "ssh ${sshUser}@${sshServer} bash -s < ${scriptFile}"
        textWithColor("Finished Deploy Container")
    }
}
