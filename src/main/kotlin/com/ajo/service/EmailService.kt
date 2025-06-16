package com.ajo.service

import com.ajo.model.Mail
import com.ajo.repository.MailRepository
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    private val mailRepository: MailRepository
) {
    fun sendNewCheckNotification() {
        val mail =mailRepository.getReferenceById(1)
        val message = SimpleMailMessage()
        message.setTo(mail.mail)
        message.subject = "Новый чек получен"
        message.text = "Просмотреть можно тут: \n https://limkorm-check-bot.demo.onlinebees.ru/adminPanel/check/index"

        mailSender.send(message)
    }
    fun sendNewWinnerNotifi() {
        val mail =mailRepository.getReferenceById(1)
        val message = SimpleMailMessage()
        message.setTo(mail.mail)
        message.subject = ""
        message.text = "Есть новый победитель"

        mailSender.send(message)
    }
}