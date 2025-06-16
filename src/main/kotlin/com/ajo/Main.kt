import com.ajo.model.CheckStatus
import com.ajo.recognize.*
import com.ajo.service.YaService
import java.io.File

//fun main(){
//    val file = File("E:\\dekstop\\ajo\\src\\main\\resources\\2.jpg")
//    val excelFile = File("E:\\dekstop\\ajo\\src\\main\\resources\\AJO.xlsx")
//    val products = readProductsFromExcel(excelFile)
//    val clientInfo = YaService().getClientInfo(file,products)
//    println(clientInfo)
//    val clients = readClientsFromExcel(excelFile)
//    val stastus =  if (findClientInCheck(clientInfo!!, clients)) {
//        CheckStatus.scanned_success
//    } else { CheckStatus.manual_review }
//    println(stastus)
//    println("extractClientInfo: $clientInfo")
//}