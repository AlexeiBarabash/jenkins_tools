#!/usr/bin/env groovy
def call(checkTriggered = true) {
    teamsSendHelper(checkTriggered)
    newSlackSendHelper(checkTriggered)
}
