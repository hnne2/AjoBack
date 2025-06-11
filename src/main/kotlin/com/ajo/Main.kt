import com.ajo.*
import com.ajo.model.CheckStatus
import com.ajo.service.YaService
import java.io.File

fun main(){
    val file = File("E:\\dekstop\\ajo\\src\\main\\resources\\1.jpg")
    val result = YaService().recognize(file)
    val recognizedText = extractTextFromJson(result)
    println(recognizedText)
    val clientInfo = extractClientInfo(recognizedText)
    val excelFile = File("E:\\dekstop\\ajo\\src\\main\\resources\\AJO.xlsx")
    val clients = readClientsFromExcel(excelFile)
    val stastus =  if (findClientInCheck(clientInfo!!, clients)) {
        CheckStatus.scanned_success
    } else { CheckStatus.manual_review }
    println(stastus)
    println("extractClientInfo: $clientInfo")
}