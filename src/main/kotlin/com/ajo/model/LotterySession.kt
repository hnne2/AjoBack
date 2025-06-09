package com.ajo.model

import jakarta.persistence.*

@Entity
data class LotterySession(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val lotteryId: String,
    var status: String,

    @ManyToOne
    @JoinColumn(name = "prize_id")
    val prize: PrizeInventory? = null,
    var ipAddress: String? = null,

    val prizeCardIndex: Int? = null
)