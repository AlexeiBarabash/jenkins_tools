#!/usr/bin/env groovy
def call(checkTriggered = false, ignoreResult = true,extraString = '') {
    teamsSendHelper(checkTriggered, ignoreResult)
    newSlackSendHelper(checkTriggered, ignoreResult, extraString)
}
