#!/usr/bin/env groovy
def call(val) {
    def changeLogSets = currentBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            def files = new ArrayList(entry.affectedFiles)
            for (int k = 0; k < files.size(); k++) {
                def file = files[k]
                def ext = (file.path.substring(file.path.lastIndexOf('.')+1)).toLowerCase()
                if(ext != 'groovy' && ext != 'md') {
                    echo "  ${file.editType.name} ${file.path}"
                    return true
                }
            }
        }
    }
    return false
}
