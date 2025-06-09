package com.ajo.service

import com.ajo.model.CallbackRequest
import com.ajo.model.Feedback
import com.ajo.repository.FeedbackRepository
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val repository: FeedbackRepository
) {
    fun saveFeedback(request: CallbackRequest): Feedback {
        val feedback = Feedback(
            name = request.name,
            tel = request.tel,
            email = request.email,
            prize = request.prize,
            lotteryId = request.lotteryId
        )
        return repository.save(feedback)
    }
}
