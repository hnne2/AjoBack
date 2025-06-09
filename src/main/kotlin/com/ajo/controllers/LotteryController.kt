package com.ajo.controllers

import com.ajo.model.Image
import com.ajo.model.LotterySession
import com.ajo.model.LotterySessionDto
import com.ajo.model.PrizeDto

import com.ajo.service.LotteryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ajo/lottery")
class LotteryController(
    private val service: LotteryService
) {
    @GetMapping("/{lotteryId}")
    fun getSession(@PathVariable lotteryId: String): LotterySessionDto? {
        val session = service.getSessionByLotteryId(lotteryId) ?: return null
        val prizeInventory = session.prize
        val prizeDto = prizeInventory?.let {
            PrizeDto(
                id = it.id,
                type = prizeInventory.type,
                label = prizeInventory.label,
                description = prizeInventory.description,
                image = prizeInventory.image?.let { url ->
                    Image(url = url, alt = prizeInventory.label ?: "image")
                },
            )

        }
        val sessionDto = LotterySessionDto(
            lotteryId = session.lotteryId,
            status = session.status,
            prize = prizeDto,
            prizeCardIndex = session.prizeCardIndex
        )
       return sessionDto
    }

}