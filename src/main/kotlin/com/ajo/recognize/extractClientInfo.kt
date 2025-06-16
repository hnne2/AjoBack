package com.ajo.recognize
import com.ajo.recognize.model.RecognizeCheck
import java.io.File
import org.springframework.web.multipart.MultipartFile

fun extractClientInfo(checkText: String): RecognizeCheck {
    val innAfterWordRegex = Regex("(?i)ИНН\\s*(\\d{10,12})")
    val innRegex = Regex("\\b\\d{12}\\b") // запасной вариант

    val inn = innAfterWordRegex.find(checkText)?.groupValues?.get(1)
        ?: innRegex.find(checkText)?.value

    val keywords = setOf("ajo", "ajd", "айо", "айщ")
    val lines = checkText.lines().filter { it.isNotBlank() }

    for (line in lines) {
        val words = line
            .lowercase()
            .split(" ", "\t", ".", ",", ":", ";", "!", "?", "(", ")")
            .filter { it.isNotBlank() }

        if (words.any { it in keywords }) {
            return RecognizeCheck(name = "AJO", inn = inn)
        }
    }

    return RecognizeCheck(name = null, inn = inn)
}


fun parseIamTokenFromResponse(jsonResponse: String): String {
    val regex = """"iamToken"\s*:\s*"([^"]+)"""".toRegex()
    return regex.find(jsonResponse)?.groupValues?.get(1)
        ?: throw IllegalArgumentException("Invalid JSON response: iamToken not found")
}


data class ProductEntry(
    val original: String,
    val normalized: String
)


fun convertMultipartFileToFile(multipartFile: MultipartFile): File {
    val tempFile = File.createTempFile("upload-", multipartFile.originalFilename ?: "temp")
    multipartFile.transferTo(tempFile)
    tempFile.deleteOnExit() // Удаляет файл при завершении работы программы
    return tempFile
}
