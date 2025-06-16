package com.ajo.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "legal_texts")
data class LegalTexts(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "rules_content", columnDefinition = "TEXT")
    val rulesContent: String,

    @Column(name = "politika_content", columnDefinition = "TEXT")
    val politikaContent: String,

    @Column(name = "agreement_content", columnDefinition = "TEXT")
    val agreementContent: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = null,
    @Column(name = "email")
    val email: String? = null,


    )