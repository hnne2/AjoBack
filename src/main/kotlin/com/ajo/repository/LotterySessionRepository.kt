package com.ajo.repository

import com.ajo.model.LotterySession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LotterySessionRepository : JpaRepository<LotterySession, Long> {
    fun findByLotteryId(lotteryId: String): LotterySession?
    fun findByIpAddress(ip:String):LotterySession?
}