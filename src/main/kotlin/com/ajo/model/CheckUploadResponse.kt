package com.ajo.model

data class SeoResponse(
    val H1: String
)

data class CheckUploadResponse(
    val status: String,
    val lottery_id: String?,
    val seo: SeoResponse = SeoResponse("Загрузите чек для участия в розыгрыше")
)