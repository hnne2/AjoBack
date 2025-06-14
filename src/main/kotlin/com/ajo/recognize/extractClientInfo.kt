package com.ajo.recognize
import com.ajo.recognize.model.RecognizeCheck
import java.io.File
import org.springframework.web.multipart.MultipartFile

fun extractClientInfo(checkText: String,products:List<String>): RecognizeCheck? {
    val normalizedProducts = products.map { ProductEntry(it, normalize(it)) }
    val innRegex = Regex("\\b\\d{12}\\b")
    val uniqueIdRegex = Regex("\\b\\d{16}\\b")

    val inn = innRegex.find(checkText)?.value
    val uniqueId = uniqueIdRegex.find(checkText) ?: return null


    val lines = checkText.lines().filter { it.isNotBlank() }

    var bestMatch: ProductEntry? = null
    var bestScore = 0.0

    for (line in lines) {
        for (product in normalizedProducts) {
            val sim = similarity(line, product.original)
            if (sim > bestScore) {
                bestScore = sim
                bestMatch = product
            }
        }
    }

    return if (bestScore >= 0.3) {
        RecognizeCheck(bestMatch!!.original, inn, uniqueId.value.toLong())
    }
    else RecognizeCheck(inn = inn, id =  uniqueId.value.toLong(), name = null)


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
