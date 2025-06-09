package com.ajo.service

import com.ajo.model.Check
import com.ajo.model.CheckStatus
import com.ajo.repository.CheckRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CheckService(
    private val checkRepository: CheckRepository
) {
    fun getAllChecks(): List<Check> = checkRepository.findAll()

    fun getPendingChecks(): List<Check> = checkRepository.findByStatus(CheckStatus.manual_review)

    fun markPrizeSent(checkId: Long) {
        val check = checkRepository.findById(checkId).orElseThrow { Exception("Check not found") }
        checkRepository.save(check.copy(isPrizeSent = true))
    }
    fun save(check: Check){
        checkRepository.save(check)
    }
    fun findCheck(id:Long):Optional<Check>{
       return checkRepository.findById(id)
    }
    fun findCheckByHash(hash:Long):Optional<Check>{
        return checkRepository.findByHash(hash)
    }
}
