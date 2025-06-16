package com.ajo.controllers

import com.ajo.model.*
import com.ajo.recognize.*
import com.ajo.recognize.readProductsFromExcel
import com.ajo.service.CheckService
import com.ajo.service.EmailService
import com.ajo.service.LotteryService
import com.ajo.service.YaService
import jakarta.servlet.http.HttpServletRequest
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
    private val emailService: EmailService,
    private val yaService: YaService
){


    @PostMapping("/upload")
    fun verify(@RequestParam file: MultipartFile, @RequestParam(value = "chek_id", required = false) checkId: String?
    ): ResponseEntity<CheckUploadResponse> {
        val excelFile = File("/home/limkorm-check-bot/upload/AJO.xlsx")
        val clients = readClientsFromExcel(excelFile)
        if (file.isEmpty || checkId.isNullOrEmpty()) {
            return ResponseEntity(CheckUploadResponse("error", null), HttpStatus.BAD_REQUEST)
        }
        val filename = "${UUID.randomUUID()}_${StringUtils.cleanPath(file.originalFilename!!)}"
        imageController.uploadImage(file, filename)
        val clientInfo = yaService.getClientInfo(convertMultipartFileToFile(file)) ?: return ResponseEntity(CheckUploadResponse("error", null), HttpStatus.BAD_REQUEST)
        if (!clientInfo.inn.isNullOrEmpty()){
            val existingCheck = checkService.findChecksByHash(clientInfo.inn.toLong())
            if (existingCheck.isNotEmpty()) {
                return ResponseEntity(CheckUploadResponse("error", null), HttpStatus.BAD_REQUEST)
                // чек уже был загружен
            }
        }
        emailService.sendNewCheckNotification()
        checkService.saveRecognizeCheck(filename, checkId = checkId.toLong(),clientInfo,clients)
        return ResponseEntity(CheckUploadResponse("check", null ), HttpStatus.OK)
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