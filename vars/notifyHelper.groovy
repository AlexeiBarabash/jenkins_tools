#!/usr/bin/env groovy
def call(checkTriggered = false, ignoreResult = false) {
    teamsSendHelper(checkTriggered, ignoreResult)
    newSlackSendHelper(checkTriggered, ignoreResult)
}
