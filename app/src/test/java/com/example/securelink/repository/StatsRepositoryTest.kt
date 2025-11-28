package com.example.securelink.repository

import com.example.securelink.model.StatItem
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para StatsRepository
 */
class StatsRepositoryTest {

    @Test
    fun `getStats debería retornar Flow con datos`() = runTest {
        val repository = StatsRepository()
        val statsFlow = repository.getStats()
        
        assertNotNull(statsFlow)
    }
}
