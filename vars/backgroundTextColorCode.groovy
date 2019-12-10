#!/usr/bin/env groovy
// ex: \033[${backgroundTextColorCode(color)}m text with color \033[0m
def call(color = 'blue') {
    color = color.toLowerCase()
    if(color == 'black') {
        return "40"
    } else if(color == 'red') {
        return "41"
    } else if(color == 'green') {
        return "42"
    } else if(color == 'orange') {
        return "43"
    } else if(color == 'magenta') {
        return "45"
    } else if(color == 'cyan') {
        return "46"
    } else if(color == 'cyan') {
        return "47"
    }
    return "44"
}

