#!/usr/bin/env groovy
def call(checkTriggered = true, ignoreResult = false) {
    teamsSendHelper(checkTriggered, ignoreResult)
    newSlackSendHelper(checkTriggered, ignoreResult)
}
