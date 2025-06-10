package com.ajo.service

import com.ajo.model.PrizeInventory
import com.ajo.model.PrizeType
import com.ajo.repository.PrizeInventoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PrizeInventoryService (
    private val prizeInventoryRepository: PrizeInventoryRepository
){
    fun getAllPrizes(): List<PrizeInventory> = prizeInventoryRepository.findAll()

    fun getPrizeByType(type: PrizeType): PrizeInventory? =
        prizeInventoryRepository.findByType(type)

    fun getPrizeByLabel(label: String): PrizeInventory? =
        prizeInventoryRepository.findByLabel(label)

    @Transactional
    fun incrementPrizeCounters(type: PrizeType): Boolean {
        val prize = prizeInventoryRepository.findByTypeForUpdate(type) ?: return false

        if (prize.wonTotal >= prize.totalQuantity) return false
        if (prize.wonToday >= prize.dailyLimit) return false
        if (prize.wonThisWeek >= prize.weeklyLimit) return false

        prize.wonTotal++
        prize.wonToday++
        prize.wonThisWeek++
        prize.updatedAt = LocalDateTime.now()

        prizeInventoryRepository.save(prize)
        return true
    }
}