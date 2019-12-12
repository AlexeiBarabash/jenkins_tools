#!/usr/bin/env groovy
def call() {
    try {
        textWithColor("getBuildNameFromCommits")
        def changeLogSets = currentBuild.changeSets
        def maxDate = null
        def lastCommitEntry = null
        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                def entryDate = new Date(entry.timestamp)
                def needToUpdateMaxDate = maxDate == null || maxDate < entryDate
                maxDate = needToUpdateMaxDate ? entryDate : maxDate
                lastCommitEntry = needToUpdateMaxDate ? entry : lastCommitEntry
            }
        }
        textWithColor("getBuildNameFromCommits done - ${lastCommitEntry}", "green")
        return lastCommitEntry
    } catch(Exception ex) {
        env.BUILDER_NAME = 'unknown'
        textWithColor("getBuildNameFromCommits Error", "red")
        echo ex.toString()
        return null
    }
}


