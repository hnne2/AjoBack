package com.ajo
import java.io.File
import com.google.gson.JsonParser
import org.apache.commons.text.similarity.LevenshteinDistance
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.web.multipart.MultipartFile


fun parseIamTokenFromResponse(jsonResponse: String): String {
    val regex = """"iamToken"\s*:\s*"([^"]+)"""".toRegex()
    return regex.find(jsonResponse)?.groupValues?.get(1)
        ?: throw IllegalArgumentException("Invalid JSON response: iamToken not found")
}
val products = listOf(
    "AJO Dog Sense Сухой полнорационный корм с гречкой для собак с чувствительным пищеварением 12 кг",
    "AJO Dog Mini Hypoallergenic Сухой полнорационный корм с гречкой для взрослых собак миниатюрных и малых пород, склонных к аллергиям 2 кг",
    "AJO Dog Mini Hypoallergenic Сухой полнорационный корм с гречкой для взрослых собак миниатюрных и малых пород, склонных к аллергиям 12 кг",
    "AJO Dog Hypoallergenic Сухой полнорационный корм с гречкой для взрослых собак средних и крупных пород, склонных к аллергиям 2 кг",
    "AJO Dog Hypoallergenic Сухой полнорационный корм с гречкой для взрослых собак средних и крупных пород, склонных к аллергиям 12 кг",
    "AJO Dog Very Small Adult Сухой полнорационный корм с гречкой для взрослых собак миниатюрных пород 1,5 кг",
    "AJO Dog Very Small Adult Сухой полнорационный корм с гречкой для взрослых собак миниатюрных пород 8 кг",
    "AJO Dog Very Small Puppy & Junior Сухой полнорационный корм с гречкой для щенков миниатюрных пород 1,5 кг",
    "AJO Dog Very Small Puppy & Junior Сухой полнорационный корм с гречкой для щенков миниатюрных пород 8 кг",
    "AJO Dog Mini Puppy & Junior Сухой полнорационный корм с гречкой для щенков малых пород 2 кг",
    "AJO Dog Mini Puppy & Junior Сухой полнорационный корм с гречкой для щенков малых пород 8 кг",
    "AJO Dog Mini Adult Сухой полнорационный корм с гречкой для взрослых собак малых пород 2 кг",
    "AJO Dog Mini Adult Сухой полнорационный корм с гречкой для взрослых собак малых пород 8 кг",
    "AJO Dog PUPPY & JUNIOR Сухой полнорационный корм с гречкой для щенков и молодых собак средних и крупных пород 2 кг",
    "AJO Dog PUPPY & JUNIOR Сухой полнорационный корм с гречкой для щенков и молодых собак средних и крупных пород 12 кг",
    "AJO Dog Medium Adult Сухой полнорационный корм с гречкой для взрослых собак средних пород 2 кг",
    "AJO Dog Medium Adult Сухой полнорационный корм с гречкой для взрослых собак средних пород 12 кг",
    "AJO Dog Maxi Adult Сухой полнорационный корм с гречкой для взрослых собак крупных пород 2 кг",
    "AJO Dog Maxi Adult Сухой полнорационный корм с гречкой для взрослых собак крупных пород 12 кг",
    "AJO Cat Kitten & Mom Сухой полнорационный корм для котят, беременных и кормящих кошек 0,4 кг",
    "AJO Cat Kitten & Mom Сухой полнорационный корм для котят, беременных и кормящих кошек 1,5 кг",
    "AJO Cat Kitten & Mom Сухой полнорационный корм для котят, беременных и кормящих кошек 10 кг",
    "AJO Cat Аctive Сухой полнорационный корм для взрослых кошек 0,4 кг",
    "AJO Cat Аctive Сухой полнорационный корм для взрослых кошек 1,5 кг",
    "AJO Cat Аctive Сухой полнорационный корм для взрослых кошек 10 кг",
    "AJO Cat Grand Master Сухой полнорационный корм для кошек старшего возраста 0,4 кг",
    "AJO Cat Grand Master Сухой полнорационный корм для кошек старшего возраста 1,5 кг",
    "AJO Cat Grand Master Сухой полнорационный корм для кошек старшего возраста 10 кг",
    "AJO Cat Sterile Сухой полнорационный корм для активных стерилизованных кошек с высоким содержанием белка 0,4 кг",
    "AJO Cat Sterile Сухой полнорационный корм для активных стерилизованных кошек с высоким содержанием белка 1,5 кг",
    "AJO Cat Sterile Сухой полнорационный корм для активных стерилизованных кошек с высоким содержанием белка 10 кг",
    "AJO Cat Sterile Weight Control Сухой полнорационный корм для стерилизованных кошек контроль веса 0,4 кг",
    "AJO Cat Sterile Weight Control Сухой полнорационный корм для стерилизованных кошек контроль веса 1,5 кг",
    "AJO Cat Sterile Weight Control Сухой полнорационный корм для стерилизованных кошек контроль веса 10 кг",
    "AJO Cat Sense Сухой полнорационный корм для кошек с чувствительным пищеварением 0,4 кг",
    "AJO Cat Sense Сухой полнорационный корм для кошек с чувствительным пищеварением 1,5 кг",
    "AJO Cat Sense Сухой полнорационный корм для кошек с чувствительным пищеварением 10 кг",
    "AJO Cat Delicate Taste Сухой полнорационный корм для привередливых кошек и котят 0,4 кг",
    "AJO Cat Delicate Taste Сухой полнорационный корм для привередливых кошек и котят 1,5 кг",
    "AJO Cat Delicate Taste Сухой полнорационный корм для привередливых кошек и котят 10 кг",
    "AJO Cat Skin & Hair Сухой полнорационный корм для кошек здоровая кожа и красивая шерсть 0,4 кг",
    "AJO Cat Skin & Hair Сухой полнорационный корм для кошек здоровая кожа и красивая шерсть 1,5 кг",
    "AJO Cat Skin & Hair Сухой полнорационный корм для кошек здоровая кожа и красивая шерсть 10 кг"
)


val levenshtein = LevenshteinDistance()

fun similarity(s1: String, s2: String): Double {
    val distance = levenshtein.apply(s1.lowercase(), s2.lowercase())
    val maxLen = maxOf(s1.length, s2.length)
    if (maxLen == 0) return 1.0
    return 1.0 - distance.toDouble() / maxLen
}


fun extractClientInfo(checkText: String): RecognizeCheck? {
    val innRegex = Regex("\\b\\d{12}\\b")
    val uniqueIdRegex = Regex("\\b\\d{16}\\b")

    val inn = innRegex.find(checkText)?.value ?: return null
    val uniqueId = uniqueIdRegex.find(checkText)?.value ?: return null

    val lines = checkText.lines().filter { it.isNotBlank() }

    for (line in lines) {
        for (product in products) {
            val sim = similarity(line.trim(), product.trim())
            if (sim >= 0.08) { // Порог похожести
                return RecognizeCheck(product, inn, uniqueId.toLong())
            }
        }
    }

    return null
}



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



data class ClientInfo(
    val name: String,
    val inn: String,
    val id:String
)
data class RecognizeCheck(
    val name: String,
    val inn: String,
    val id:Long
)

fun readClientsFromExcel(file: File): List<ClientInfo> {
    val clients = mutableListOf<ClientInfo>()
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheetAt(0)

    for (row in sheet.drop(1)) { // Пропускаем заголовок
        val clientId = row.getCell(0)?.toString()?.trim() ?: ""
        val name = row.getCell(1)?.toString()?.trim() ?: ""
        val inn = row.getCell(3)?.toString()?.trim() ?: ""
        clients.add(ClientInfo(name, inn,clientId))
    }

    workbook.close()
    return clients
}
fun findClientInCheck(recognizeCheck: RecognizeCheck, clients: List<ClientInfo>): Boolean {
    for (client in clients) {
        if (client.inn==recognizeCheck.inn) {
            return true
        }
    }
    return false
}
fun convertMultipartFileToFile(multipartFile: MultipartFile): File {
    val tempFile = File.createTempFile("upload-", multipartFile.originalFilename ?: "temp")
    multipartFile.transferTo(tempFile)
    tempFile.deleteOnExit() // Удаляет файл при завершении работы программы
    return tempFile
}
