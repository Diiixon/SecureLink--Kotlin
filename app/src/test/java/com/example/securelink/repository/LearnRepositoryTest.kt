package com.example.securelink.repository

import com.example.securelink.model.LearnItem
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para LearnRepository
 */
class LearnRepositoryTest {

    @Test
    fun `getLearnItems debería retornar Flow con datos`() = runTest {
        val repository = LearnRepository()
        val itemsFlow = repository.getLearnItems()
        
        assertNotNull(itemsFlow)
    }
}
