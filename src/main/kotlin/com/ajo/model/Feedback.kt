package com.ajo.model

import jakarta.persistence.*

@Entity
data class Feedback(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,
    val tel: String,
    val email: String,
    val prize: String,
    val lotteryId: String
)
