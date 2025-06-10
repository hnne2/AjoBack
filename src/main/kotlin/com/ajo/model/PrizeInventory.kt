package com.ajo.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "prize_inventory")
data class PrizeInventory
(
    @Id
    val id:String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    val type: PrizeType,

    @Column(name = "total_quantity", nullable = false)
    var totalQuantity: Int,

    @Column(name = "won_total", nullable = false)
    var wonTotal: Int = 0,

    @Column(name = "won_today", nullable = false)
    var wonToday: Int = 0,

    @Column(name = "won_this_week", nullable = false)
    var wonThisWeek: Int = 0,

    @Column(name = "daily_limit", nullable = false)
    val dailyLimit: Int,

    @Column(name = "weekly_limit", nullable = false)
    val weeklyLimit: Int,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "label")
    val label:String?,
    @Column(name = "description")
    val description:String?,
    @Column(name = "image")
    val image:String?,
    @Column(name = "probability")
    val probability:Double = 0.0
)

enum class PrizeType(){
    tshirt,ozon_card,vk_fest_ticket
}



