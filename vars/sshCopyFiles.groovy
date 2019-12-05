def call(src, dest, server, user = 'deploy') {
    bashCommand("ssh ${user}@${server} rm -rf ${dest}")
    bashCommand("ssh ${user}@${server} mkdir -m 777 -p ${dest}")
    bashCommand("scp ${src} ${user}@${server}:${dest}")
}
