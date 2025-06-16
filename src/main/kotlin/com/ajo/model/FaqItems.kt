package com.ajo.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "faq_items")
data class FaqItems(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val title: String? = "Вопрос-ответ",

    val label: String,

    @Column(columnDefinition = "TEXT")
    val content: String,

    val createdAt: LocalDateTime? = null
)
