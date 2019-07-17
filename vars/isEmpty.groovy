#!/usr/bin/env groovy
def call(val) {
    try {
        if(val != null && val != '') {
            return false;
        }
    } catch(Exception ex) {
        return true;
    }
    return true;
}
