package com.dropbox.core

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by hitenpratap on 2/2/14.
 */
class DropBoxService {

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
            return response
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
        try{
            String response = url.text
            return response
        }catch (FileNotFoundException ex){
            return null
        }
    }


    def createNewFolder(String root,String path,String accessToken){
        StringBuilder tokenUri=new StringBuilder("root=")
        tokenUri.append(URLEncoder.encode(root,"UTF-8"))
        tokenUri.append("&path=")
        tokenUri.append(URLEncoder.encode(path,"UTF-8"))
        URL url=new URL("https://api.dropbox.com/1/fileops/create_folder?access_token=${accessToken}")
        HttpURLConnection connection
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + tokenUri.toString().length());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(tokenUri.toString());
            outputStreamWriter.flush();
            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream())
            String response=inputStreamReader.text
            return response

        } finally {
            connection?.disconnect()
        }
    }

    def copyOps(String root,String fromPath,String toPath,String accessToken) {
        StringBuilder tokenUri=new StringBuilder("root=")
        tokenUri.append(URLEncoder.encode(root,"UTF-8"))
        tokenUri.append("&from_path=")
        tokenUri.append(URLEncoder.encode(fromPath,"UTF-8"))
        tokenUri.append("&to_path=")
        tokenUri.append(URLEncoder.encode(toPath,"UTF-8"))
        URL url=new URL("https://api.dropbox.com/1/fileops/copy?access_token=${accessToken}")
        HttpURLConnection connection
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + tokenUri.toString().length());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(tokenUri.toString());
            outputStreamWriter.flush();
            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream())
            String response=inputStreamReader.text
            return response

        } finally {
            connection?.disconnect()
        }
    }

    def deleteFileOps(String root,String path,String accessToken){
        StringBuilder tokenUri=new StringBuilder("root=")
        tokenUri.append(URLEncoder.encode(root,"UTF-8"))
        tokenUri.append("&path=")
        tokenUri.append(URLEncoder.encode(path,"UTF-8"))
        URL url=new URL("https://api.dropbox.com/1/fileops/delete?access_token=${accessToken}")
        HttpURLConnection connection
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + tokenUri.toString().length());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(tokenUri.toString());
            outputStreamWriter.flush();
            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream())
            String response=inputStreamReader.text
            return response

        } finally {
            connection?.disconnect()
        }
    }

    def moveOps(String root,String fromPath,String toPath,String accessToken) {
        StringBuilder tokenUri=new StringBuilder("root=")
        tokenUri.append(URLEncoder.encode(root,"UTF-8"))
        tokenUri.append("&from_path=")
        tokenUri.append(URLEncoder.encode(fromPath,"UTF-8"))
        tokenUri.append("&to_path=")
        tokenUri.append(URLEncoder.encode(toPath,"UTF-8"))
        URL url=new URL("https://api.dropbox.com/1/fileops/move?access_token=${accessToken}")
        HttpURLConnection connection
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + tokenUri.toString().length());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(tokenUri.toString());
            outputStreamWriter.flush();
            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream())
            String response=inputStreamReader.text
            return response

        } finally {
            connection?.disconnect()
        }
    }


}
