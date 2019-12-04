#!/usr/bin/env groovy
def call(val) {
    def changeLogSets = currentBuild.changeSets
    def arr = new ArrayList()
    print(changeLogSets)
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            echo "${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg}"
            def files = new ArrayList(entry.affectedFiles)
            print(files)
            arr = arr + files
            for (int k = 0; k < files.size(); k++) {
                def file = files[k]
                echo "  ${file.editType.name} ${file.path}"
                echo file.path.substring(file.path.lastIndexOf('.')+1)
            }
        }
    }
}
