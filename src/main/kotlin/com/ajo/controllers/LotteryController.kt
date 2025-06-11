package com.ajo.controllers

import com.ajo.model.*

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
                type = it.type,
                label = it.label,
                description = it.description,
                image = it.image?.let { url ->
                    Image(url = url, alt = it.label ?: "image")
                }
            )
        }

        // Пример данных для стикеров — можно брать из конфигурации или БД
        val stickersDto = StickersDto(
            link = StickerLink(
                to = "https://t.me/addstickers/AJOpet",
                label = "Получить стикеры"
            ),
            image = StickerImage(
                url = "sticker.png",
                alt = "Стикер"
            )
        )

        return LotterySessionDto(
            lotteryId = session.lotteryId,
            status = session.status,
            prize = prizeDto,
            prizeCardIndex = session.prizeCardIndex,
            stickers = stickersDto
        )
    }


}
