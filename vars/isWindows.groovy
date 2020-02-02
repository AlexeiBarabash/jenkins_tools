#!/usr/bin/env groovy
def call() {
    try {
        echo "env.isWindows = ${env.isWindows}"
        return env.isWindows == "true" || NODE_LABELS.toLowerCase().indexOf('windows') >= 0 || NODE_NAME.toLowerCase().indexOf('windows') >= 0
    } catch(Exception ex) {
        return false
    }
}
