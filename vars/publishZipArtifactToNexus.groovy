
#!/usr/bin/env groovy
def call(filePath, repo = ,user = 'jenkins:zxasqw12') {
    textWithColor("ENV")
    def fileName = JOB_NAME + '_' + BUILD_NUMBER + '.zip'
    def auth = 'Basic ' + user.bytes.encodeBase64().toString()
    httpRequest (consoleLogResponseBody: true,
            uploadFile: filePath
            httpMode: 'PUT',
            url: "http://registry.codeoasis.com:8081/repository/Files/" + fileName,
            validResponseCodes: '201',
            customHeaders: [[maskValue: false, name: 'Authorization', value: auth]]
    )
}
