package com.ajo.recognize

import com.google.gson.JsonParser

fun extractTextFromJson(jsonStr: String): String {
    val json = JsonParser.parseString(jsonStr).asJsonObject
    val results = json.getAsJsonArray("results")
    val sb = StringBuilder()
    results.forEach { result ->
        val innerResults = result.asJsonObject.getAsJsonArray("results")
        innerResults.forEach { inner ->
            val textDetection = inner.asJsonObject.get("textDetection")?.asJsonObject
            val pages = textDetection?.getAsJsonArray("pages")
            pages?.forEach { page ->
                val blocks = page.asJsonObject.getAsJsonArray("blocks")
                blocks.forEach { block ->
                    val lines = block.asJsonObject.getAsJsonArray("lines")
                    lines.forEach { line ->
                        val words = line.asJsonObject.getAsJsonArray("words")
                        words.forEach { word ->
                            val text = word.asJsonObject.get("text").asString
                            sb.append(text).append(" ")
                        }
                        sb.append("\n")
                    }
                    sb.append("\n")
                }
            }
        }
    }
    return sb.toString().trim()
}