#!/usr/bin/env groovy
def call() {
    try {
        return NODE_LABELS.toLowerCase().indexOf('windows') >= 0 || NODE_NAME.toLowerCase().indexOf('windows') >= 0
    } catch(Exception ex) {
        return false
    }
}
