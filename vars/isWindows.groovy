#!/usr/bin/env groovy
def call() {
    def notWindows = true
    try {
        notWindows = isEmpty(env.isWindows) || env.isWindows == "false" || env.isWindows == false
    } catch(Exception ex) {
        notWindows = true
    }
    return !notWindows;
}
