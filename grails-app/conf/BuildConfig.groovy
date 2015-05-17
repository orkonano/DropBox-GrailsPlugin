grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenCentral()
        mavenRepo "http://nexus-bambooarg.rhcloud.com/nexus/content/groups/public/"
    }

    dependencies {
        compile 'commons-io:commons-io:2.4'
    }

    plugins {
        build ':release:3.1.1', ':rest-client-builder:1.0.3', {
            export = false
        }
    }
}
