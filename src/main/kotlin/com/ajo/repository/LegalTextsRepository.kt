package com.ajo.repository

import com.ajo.model.LegalTexts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LegalTextsRepository : JpaRepository<LegalTexts, Long>