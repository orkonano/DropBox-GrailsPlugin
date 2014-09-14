#!/bin/bash

echo "instalando dropbox"
grails clean && grails refresh-dependencies && grails maven-install

#grails publish-plugin 

