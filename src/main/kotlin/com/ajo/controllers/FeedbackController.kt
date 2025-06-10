package com.ajo.controllers

import com.ajo.model.CallbackRequest
import com.ajo.service.FeedbackService
import com.ajo.service.LotteryService
import com.ajo.service.PrizeInventoryService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ajo/feedback")
class FeedbackController(
    private val service: FeedbackService,
    private val lotteryService: LotteryService,
    private val inventoryService: PrizeInventoryService

) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun submitFeedback(@RequestBody @Valid request: CallbackRequest,requestIp: HttpServletRequest) {
        val ip = requestIp.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()
            ?: requestIp.remoteAddr
        val session = lotteryService.getSessionByLotteryId( request.lotteryId)
        if (session!=null){
            session.status="end"
            session.ipAddress =ip
            lotteryService.saveSession(session)
        }
        
        service.saveFeedback(request)
    }
}