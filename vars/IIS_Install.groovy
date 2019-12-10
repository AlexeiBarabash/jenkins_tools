#!/usr/bin/env groovy
def call() {
    textWithColor("Install IIS")
    try {
        powershell "Set-ExecutionPolicy Bypass -Scope Process"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-WebServerRole"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-WebServer"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-CommonHttpFeatures"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-HttpErrors"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-HttpRedirect"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-ApplicationDevelopment"
        powershell "Enable-WindowsOptionalFeature -online -FeatureName NetFx4Extended-ASPNET45"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-NetFxExtensibility45"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-HealthAndDiagnostics"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-HttpLogging"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-LoggingLibraries"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-RequestMonitor"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-HttpTracing"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-Security"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-RequestFiltering"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-Performance"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-WebServerManagementTools"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-IIS6ManagementCompatibility"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-Metabase"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-ManagementConsole"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-BasicAuthentication"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-WindowsAuthentication"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-StaticContent"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-DefaultDocument"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-WebSockets"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-ApplicationInit"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-ISAPIExtensions"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-ISAPIFilter"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-HttpCompressionStatic"
        powershell "Enable-WindowsOptionalFeature -Online -FeatureName IIS-ASPNET45"
        powershell "Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))"
        powershell "choco install webdeploy -y"
        powershell "choco install urlrewrite -y"
    } catch(Exception ex) {
        textWithColor("Install IIS Error", "red")
        echo ex.toString()
    }
}
