package com.ajo.service

import com.ajo.model.LotterySession

import com.ajo.model.PrizeInventory
import com.ajo.model.PrizeType
import com.ajo.repository.LotterySessionRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class LotteryService(
    private val lotteryRepo: LotterySessionRepository,
    private val prizeInventoryService: PrizeInventoryService
) {
    fun getSessionByLotteryId(lotteryId: String): LotterySession? =
        lotteryRepo.findByLotteryId(lotteryId)

    fun saveSession(session: LotterySession): LotterySession =
        lotteryRepo.save(session)


    fun pickPrize(ip: String): PrizeInventory? {
        if (lotteryRepo.findByIpAddress(ip) != null) return null

        val prizes = prizeInventoryService.getAllPrizes()

        val totalWeight = prizes.sumOf { it.probability }
        val randomValue = Random.nextDouble(0.0, 1.0)
        if (randomValue>totalWeight)
            return null

        var cumulativeWeight = 0.0
        for (prize in prizes) {
            cumulativeWeight += prize.probability
            if (randomValue <= cumulativeWeight) {
                return prize // выбрали приз
            }
        }
        return null
    }

}
