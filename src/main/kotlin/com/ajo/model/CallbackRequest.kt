package com.ajo.model

import jakarta.validation.constraints.*

data class CallbackRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Pattern(regexp = "\\+7 \\d{3} \\d{3}-\\d{2}-\\d{2}")
    val tel: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val prize: String,

    @field:NotBlank
    val lotteryId: String
)
