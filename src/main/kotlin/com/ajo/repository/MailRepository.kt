package com.ajo.repository

import com.ajo.model.Mail
import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository : JpaRepository<Mail,Long>