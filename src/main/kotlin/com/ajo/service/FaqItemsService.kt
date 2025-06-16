package com.ajo.service

import com.ajo.model.FaqItems
import com.ajo.repository.FaqItemsRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FaqItemsService(private val faqItemsRepository: FaqItemsRepository) {

    fun findAll(): List<FaqItems> = faqItemsRepository.findAll()

    fun findById(id: Long): FaqItems =
        faqItemsRepository.findById(id).orElseThrow { NoSuchElementException("FAQ item not found") }

    fun create(faqItem: FaqItems): FaqItems {
        val toSave = faqItem.copy(createdAt = LocalDateTime.now())
        return faqItemsRepository.save(toSave)
    }

    fun update(id: Long, updated: FaqItems): FaqItems {
        val existing = findById(id)
        val toUpdate = existing.copy(
            title = updated.title,
            label = updated.label,
            content = updated.content
        )
        return faqItemsRepository.save(toUpdate)
    }

    fun delete(id: Long) = faqItemsRepository.deleteById(id)
    fun getFaqList(): List<Map<String, String>> {
        return faqItemsRepository.findAll().map {
            mapOf(
                "label" to it.label,
                "content" to it.content
            )
        }
    }
}
