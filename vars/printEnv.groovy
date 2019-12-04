#!/usr/bin/env groovy
def call() {
    textWithColor("ENV")
    bashCommand("printenv")
}
