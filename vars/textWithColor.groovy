#!/usr/bin/env groovy

def textWithColor(text) {
    echo "\033[44m --------------------------------------- \033[0m"
    echo "\033[44m ${text} \033[0m"
    echo "\033[44m --------------------------------------- \033[0m"
}