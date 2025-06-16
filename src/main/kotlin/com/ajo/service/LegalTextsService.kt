package com.ajo.service

import com.ajo.model.LegalTexts
import com.ajo.repository.LegalTextsRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LegalTextsService(private val legalTextsRepository: LegalTextsRepository) {

    fun findAll(): List<LegalTexts> = legalTextsRepository.findAll()

    fun findById(id: Long): LegalTexts =
        legalTextsRepository.findById(id).orElseThrow { NoSuchElementException("LegalText not found") }

    fun create(legalTexts: LegalTexts): LegalTexts {
        val toSave = legalTexts.copy(createdAt = LocalDateTime.now())
        return legalTextsRepository.save(toSave)
    }

    fun update(id: Long, updated: LegalTexts): LegalTexts {
        val existing = findById(id)
        val toUpdate = existing.copy(
            rulesContent = updated.rulesContent,
            politikaContent = updated.politikaContent,
            agreementContent = updated.agreementContent
        )
        return legalTextsRepository.save(toUpdate)
    }

    fun delete(id: Long) = legalTextsRepository.deleteById(id)
}
