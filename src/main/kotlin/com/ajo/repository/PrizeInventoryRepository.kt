package com.ajo.repository

import com.ajo.model.PrizeInventory
import com.ajo.model.PrizeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrizeInventoryRepository : JpaRepository<PrizeInventory,Long> {
    fun findByType(type: PrizeType): PrizeInventory?
    fun findByLabel(label:String):PrizeInventory?
}