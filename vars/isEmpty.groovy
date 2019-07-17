#!/usr/bin/env groovy
def call(val) {
    try {
        if(val != null && val != '') {
            return true;
        }
    } catch(Exception ex) {
        return false;
    }
    return false;
}
