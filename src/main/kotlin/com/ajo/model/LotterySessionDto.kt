package com.ajo.model

import jakarta.persistence.*

data class LotterySessionDto (
    val lotteryId: String,
    var status: String,
    val prize: PrizeDto? = null,
    val prizeCardIndex: Int? = null,
    val stickers: StickersDto? = null
)

data class StickerLink(
    val to: String,
    val label: String
)

data class StickerImage(
    val url: String,
    val alt: String
)

data class StickersDto(
    val link: StickerLink,
    val image: StickerImage
)
