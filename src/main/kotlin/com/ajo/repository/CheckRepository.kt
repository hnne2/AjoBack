package com.ajo.repository

import com.ajo.model.Check
import com.ajo.model.CheckStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CheckRepository : JpaRepository<Check, Long> {
    fun findByStatus(status: CheckStatus): List<Check>
    fun findByIsPrizeSentFalse(): List<Check>
    fun findByHash(hash:Long):Optional<Check>
    fun findAllByHash(hash: Long): List<Check>

}
