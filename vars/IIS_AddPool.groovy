#!/usr/bin/env groovy
def call(siteName) {
    textWithColor("IIS AddPool")
    try {
        powershell """
            Import-Module WebAdministration
            New-WebAppPool -name "${siteName}"  -force
            \$appPool = Get-Item -name "${siteName}" 
            \$appPool.processModel.identityType = "NetworkService"
            \$appPool.enable32BitAppOnWin64 = 1
            \$appPool | Set-Item
        """
    } catch(Exception ex) {
        textWithColor("IIS AddPool Error", "red")
        echo ex.toString()
    }
}
