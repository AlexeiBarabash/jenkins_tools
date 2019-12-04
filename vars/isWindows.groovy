#!/usr/bin/env groovy
def call() {
    try {
        return env.isWindows == true || env.isWindows == '' || env.isWindows == "true";
    } catch(Exception ex) {}
    return false;
}
