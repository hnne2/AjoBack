package com.ajo.service

import com.ajo.parseIamTokenFromResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import yandex.cloud.sdk.auth.IamToken
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.util.*

@Service
class YaService {
    @Value("\${yandex.oauth}")
     var token:String = "y0__xDCxtrJBxjB3RMg1uSiqhOikBrJm0It27CZQqAvrvizzj63Dw"
    @Value("\${catalog}")
     var catalog:String ="b1gatquqpsamjh30u1h1"
    fun recognize(file:File):String{
        val IamToken=generateIamToken(token)
       return recognizeText(iamToken = IamToken, imageFile = file, folderId = catalog)
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

    fun recognizeText(imageFile: File, iamToken: String, folderId: String): String {
        val imageBytes = Files.readAllBytes(imageFile.toPath())
        val base64Image = Base64.getEncoder().encodeToString(imageBytes)

        val requestBody = """
  {
  "folderId": "$folderId",
  "analyze_specs": [
    {
      "content": "$base64Image",
      "features": [
        {
          "type": "TEXT_DETECTION",
          "text_detection_config": {
            "language_codes": ["*"]
          }
        }
      ]
    }
  ]
}
""".trimIndent()

        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://vision.api.cloud.yandex.net/vision/v1/batchAnalyze"))
            .header("Authorization", "Bearer $iamToken")
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}