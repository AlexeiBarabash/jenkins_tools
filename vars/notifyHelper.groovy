#!/usr/bin/env groovy
def call(checkTriggered = false, ignoreResult = true) {
    teamsSendHelper(checkTriggered, ignoreResult)
    newSlackSendHelper(checkTriggered, ignoreResult)
}
