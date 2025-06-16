package com.ajo.repository

import com.ajo.model.FaqItems
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FaqItemsRepository : JpaRepository<FaqItems, Long>