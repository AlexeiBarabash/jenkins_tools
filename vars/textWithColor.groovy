#!/usr/bin/env groovy
def call(text, color = 'blue') {
    colorCode = "\033[${backgroundTextColorCode(color)}m"
    resetCode = "\033[0m"
    echo "${colorCode} --------------------------------------- ${resetCode}"
    echo "${colorCode} ${text} ${resetCode}"
    echo "${colorCode} --------------------------------------- ${resetCode}"
}
