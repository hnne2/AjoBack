package com.ajo.model

import jakarta.persistence.*
import java.time.LocalDateTime

data class PrizeDto(
    val id:String,
    val type: PrizeType,
    val label:String?,
    val description:String?,
    val image:Image?,
)
data class Image(
        val url:String,
        val alt:String
)