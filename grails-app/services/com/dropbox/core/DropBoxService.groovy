package com.dropbox.core

import grails.converters.JSON
import groovy.json.JsonSlurper
import org.grails.web.json.JSONElement

/**
 * Created by hitenpratap on 2/2/14.
 */
class DropBoxService {

    static transactional = false

    def accountInfo(String accessToken) {
        StringBuilder stringBuilder = new StringBuilder("https://api.dropbox.com/1/account/info")
        stringBuilder.append("?access_token=")
        stringBuilder.append(URLEncoder.encode(accessToken, "UTF-8"))
        URL url = new URL(stringBuilder.toString())
        String response = url.text
        JSONElement contents = JSON.parse(response)
        String displayName = contents.display_name
        String email = contents.email
        return displayName + '    ' + email
    }

    def dropBoxFileUpload(String root, String destinationPath,byte[] data, String accessToken) {
        URL url = new URL("https://api-content.dropbox.com/1/files_put/${root}/${destinationPath}?access_token=${accessToken}")
        HttpURLConnection connection
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.setDoOutput(true)
            connection.setRequestMethod("PUT")
            connection.setRequestProperty("Content-Type", "mime/type");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length))
            OutputStream outputStream = connection.getOutputStream()
            outputStream.write(data)
            outputStream.flush()
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())
            String response = inputStreamReader.text
            new JsonSlurper().parseText(response)
        }
        finally {
            connection.disconnect()
        }
    }

    def getObjectMetaData(String root,String path,String accessToken){
        StringBuilder stringBuilder = new StringBuilder("https://api.dropbox.com/1/metadata/${root}/${path}")
        stringBuilder.append("?access_token=")
        stringBuilder.append(URLEncoder.encode(accessToken, "UTF-8"))
        URL url = new URL(stringBuilder.toString())
        String response = url.text
        new JsonSlurper().parseText(response)
    }


    def createNewFolder(String root, String path, String accessToken){
        connectClient("fileops/create_folder", accessToken, "POST", root, [path: path])
    }

    def copyOps(String root, String fromPath, String toPath, String accessToken) {
        connectClient("fileops/copy", accessToken, "POST", root, [fromPath: fromPath, toPath: toPath])
    }

    def deleteFileOps(String root, String path, String accessToken){
        connectClient("fileops/delete", accessToken, "POST",root, [path: path])
    }

    def moveOps(String root, String fromPath, String toPath, String accessToken) {
        connectClient("fileops/move", accessToken, "POST", root, [fromPath: fromPath, toPath: toPath])
    }

    def permanentlyDeleteOps(String root, String path, String accessToken) {
        connectClient("fileops/permanently_delete", accessToken, "POST", root, [path: path])
    }

    def private connectClient(String action, String accessToken, String httpMethod, String root, Map<String, String> params){
        StringBuilder tokenUri=new StringBuilder("root=")
        tokenUri.append(URLEncoder.encode(root, "UTF-8"))
        params.each{ Map.Entry<String, String> entry ->
            tokenUri.append("&$entry.key=").append(URLEncoder.encode(entry.value, "UTF-8"))
        }
        URL url = new URL("https://api.dropbox.com/1/${action}?access_token=${accessToken}")
        HttpURLConnection connection
        try {
            connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod(httpMethod)
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            connection.setRequestProperty("Content-Length", "" + tokenUri.toString().length())
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream())
            outputStreamWriter.write(tokenUri.toString())
            outputStreamWriter.flush()
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())
            String response = inputStreamReader.text
            new JsonSlurper().parseText(response)
        } finally {
            connection?.disconnect()
        }
    }

    InputStream getFile(String root, String accessToken, String path){
        StringBuilder stringBuilder = new StringBuilder("https://api-content.dropbox.com/1/files/${root}/${path}")
        stringBuilder.append("?access_token=")
        stringBuilder.append(URLEncoder.encode(accessToken, "UTF-8"))
        new URL(stringBuilder.toString()).newInputStream()
    }
}
