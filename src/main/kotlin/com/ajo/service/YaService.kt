package com.ajo.service

import com.ajo.recognize.*
import com.ajo.recognize.model.RecognizeCheck
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class YaService {
    @Value("\${yandex.oauth}")
     var token:String = "y0__xDCxtrJBxjB3RMg1uSiqhOikBrJm0It27CZQqAvrvizzj63Dw"
    @Value("\${catalog}")
     var catalog:String ="b1gatquqpsamjh30u1h1"
    fun getClientInfo(file: File): RecognizeCheck? {
       // val file = convertMultipartFileToFile(multipartFile)
        val iamToken=generateIamToken(token)
       val recognizeText = recognizeText(iamToken = iamToken, imageFile = file, folderId = catalog)
        val extractText = extractTextFromJson(recognizeText)
       return extractClientInfo(extractText)
    }
    fun generateIamToken(oauthToken: String): String {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://iam.api.cloud.yandex.net/iam/v1/tokens"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"yandexPassportOauthToken\":\"$oauthToken\"}"))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return parseIamTokenFromResponse(response.body())
    }


}