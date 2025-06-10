package com.ajo.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "mail")
data class Mail(
    @Id
    val id: Int = 1,  

    @Column(name = "mail_address")
    var mail: String
)