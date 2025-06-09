package com.ajo.model

import jakarta.persistence.*

data class LotterySessionDto (
    val lotteryId: String,
    var status: String,
    val prize: PrizeDto? = null,
    val prizeCardIndex: Int? = null
)
