#!/usr/bin/env groovy
def call() {
    textWithColor("Print ENV")
    bashCommand("printenv")
}
