package dropbox.grailsplugin

import grails.plugins.*

class DropBoxGrailsPluginGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.1.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = 'Drop Box Plugin for Grails'
    def description = 'Adds integration with the DropBox API. Here with this plugin you can use all DropBox services within from your Grails application'
    def documentation = 'https://github.com/hitenpratap/DropBox-GrailsPlugin/wiki/DropBox-for-Grails'
    def license = 'APACHE'
    def developers = [
            [name: 'Hiten Pratap Singh', email: 'hitenpratap99@gmail.com',github:'https://github.com/hitenpratap']
    ]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/hitenpratap/DropBox-GrailsPlugin/issues']
    def scm = [url: 'https://github.com/hitenpratap/DropBox-GrailsPlugin']
}
