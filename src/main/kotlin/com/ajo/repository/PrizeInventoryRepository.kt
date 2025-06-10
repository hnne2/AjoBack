package com.ajo.repository

import com.ajo.model.PrizeInventory
import com.ajo.model.PrizeType
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PrizeInventoryRepository : JpaRepository<PrizeInventory,Long> {
    fun findByType(type: PrizeType): PrizeInventory?
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PrizeInventory p WHERE p.type = :type")
    fun findByTypeForUpdate(@Param("type") type: PrizeType): PrizeInventory?
    fun findByLabel(label:String):PrizeInventory?
}