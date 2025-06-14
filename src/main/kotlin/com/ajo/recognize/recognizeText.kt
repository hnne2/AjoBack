package com.ajo.recognize

import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.util.*

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