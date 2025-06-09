package com.ajo.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.sql.Timestamp

@Entity
@Table(name = "checks")
data class Check(
    @Id
    val id: Long = 0,
    val inn: String? = null,
    val title: String? = null,

    @Enumerated(EnumType.STRING)
    val status: CheckStatus = CheckStatus.manual_review,

    @Column(columnDefinition = "TEXT")
    val moderationComment: String? = null,

    @Column(nullable = false, insertable = false, updatable = false)
    val uploadedAt: Timestamp? = null,

    val processedAt: Timestamp? = null,

    val isPrizeSent: Boolean = false ,

    val imageFilename: String? = null,
    @Version
    @Column(name = "version")
    var version: Long? = null,
    @ManyToOne
    @JoinColumn(name = "lottery_session")
    var lotterySession: LotterySession?,
    @Column(name = "hash")
    var hash:Long?
)

enum class CheckStatus {
    scanned_success, manual_review,rejected
}
