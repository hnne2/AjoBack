package com.ajo.service

import com.ajo.model.Check
import com.ajo.model.CheckStatus
import com.ajo.recognize.findClientInCheck
import com.ajo.recognize.model.ClientInfo
import com.ajo.recognize.model.RecognizeCheck
import com.ajo.repository.CheckRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CheckService(
    private val checkRepository: CheckRepository
) {
    fun save(check: Check){
        checkRepository.save(check)
    }
    fun findCheck(id:Long):Optional<Check>{
       return checkRepository.findById(id)
    }
    fun findChecksByHash(hash: Long): List<Check> {
        return checkRepository.findAllByHash(hash)
    }
    fun saveRecognizeCheck(filename:String,checkId:Long,clientInfo:RecognizeCheck,clients:List<ClientInfo>){
        val newCheck = Check(id = checkId,
            inn = clientInfo.inn,
            title = clientInfo.name,
            imageFilename = filename,
            lotterySession = null,
            status = if (findClientInCheck(clientInfo, clients)) {
                CheckStatus.scanned_success
            } else { CheckStatus.manual_review },
            hash = clientInfo.inn?.toLong()
        )
        save(newCheck)

    }
}
