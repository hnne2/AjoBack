package com.ajo.controllers

import com.ajo.*
import com.ajo.model.*
import com.ajo.service.CheckService
import com.ajo.service.EmailService
import com.ajo.service.LotteryService
import jakarta.servlet.http.HttpServletRequest
import jakarta.ws.rs.core.Request
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*


@RestController
@RequestMapping("/ajo")
class CheckController (
    private val checkService: CheckService,
    private val imageController: ImageController,
    private val lotteryService: LotteryService,
    private val emailService: EmailService

){
    val excelFile = File("/home/limkorm-check-bot/upload//AJO.xlsx")
    val clients = readClientsFromExcel(excelFile)
    @PostMapping("/upload")
    fun verify(
    @RequestParam file: MultipartFile,
    @RequestParam(value = "chek_id", required = false) checkId: String?
    ): ResponseEntity<CheckUploadResponse> {
        if (file.isEmpty) {
            return ResponseEntity(CheckUploadResponse("error", null), HttpStatus.BAD_REQUEST)
        }
        val filename = "${UUID.randomUUID()}_${StringUtils.cleanPath(file.originalFilename!!)}"
        imageController.uploadImage(file, filename)

//        val jsonFile = File("/home/limkorm-check-bot/upload/2.json")
//        val recognizedText = extractTextFromJson(jsonFile.readText())
//        val clientInfo = extractClientInfo(recognizedText)
//        println("extractClientInfo: $clientInfo")
//
//        val newCheck: Check = if (clientInfo != null) {
//            val existingCheck = checkService.findCheckByHash(clientInfo.id)
//            if (existingCheck.isPresent) {
//                return ResponseEntity(CheckUploadResponse("error", null), HttpStatus.BAD_REQUEST)
//                // чек уже был загружен
//            }
//            Check(id = checkId?.toLong() ?: clientInfo.id, //todo
//                inn = clientInfo.inn,
//                title = clientInfo.name,
//                imageFilename = filename,
//                lotterySession = null,
//                status = if (findClientInCheck(clientInfo, clients)) {
//                        CheckStatus.manual_review
//                } else { CheckStatus.manual_review },
//                hash = clientInfo.id
//            )
//        } else { // не смог сканировать чек
//            Check(id = checkId?.toLong() ?: System.currentTimeMillis(),
//                imageFilename = filename,
//                status = CheckStatus.manual_review,
//                lotterySession = null, hash = null
//            )
//        }
        val newCheck =  Check(id = checkId?.toLong() ?: System.currentTimeMillis(),   // удалить
            imageFilename = filename,
            status = CheckStatus.manual_review,
            lotterySession = null, hash = null   //
        )
        checkService.save(newCheck)
        return if (newCheck.status == CheckStatus.scanned_success) {
            ResponseEntity(CheckUploadResponse("check", null ), HttpStatus.OK)
        } else {
            emailService.sendNewCheckNotification()
            ResponseEntity(CheckUploadResponse("check", null ), HttpStatus.OK)
        }
    }


    @GetMapping("/upload")
    fun checkInfo(@RequestParam("chek_id", required = false) checkId: String?,
                  request: HttpServletRequest
    ): ResponseEntity<CheckUploadResponse> {
        val ip = request.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()
            ?: request.remoteAddr
        if (checkId==null){
            println("ready null")
            return ResponseEntity(CheckUploadResponse(
                status = "ready",
                lottery_id = null
            ),HttpStatus.OK)
        }
        val uuid = UUID.randomUUID().toString()
        return if ( checkService.findCheck(checkId.toLong()).isPresent) {
            val findCheck =checkService.findCheck(checkId.toLong()).get()
            val status = when(findCheck.status){
                CheckStatus.scanned_success -> {
                    val lotterySession = LotterySession(
                        lotteryId =uuid ,
                        status = "ready",
                        prize = lotteryService.pickPrize(ip),
                        prizeCardIndex = Random().nextInt(0, 3)
                    )
                    lotteryService.saveSession(lotterySession)
                    findCheck.lotterySession = lotterySession
                    checkService.save(findCheck)
                    "success"
                }
                CheckStatus.manual_review -> "moderation"
                CheckStatus.rejected -> "error"
            }
            ResponseEntity.ok(CheckUploadResponse(
                status = status,
                lottery_id = uuid
            ))
        } else {
            println("ready")
            return ResponseEntity(CheckUploadResponse(

                status = "ready",
                lottery_id = null
            ),HttpStatus.OK)
        }

    }

}