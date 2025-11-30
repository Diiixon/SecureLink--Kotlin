package com.example.securelink.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para HomeViewModel
 * Prueba la inicialización y estructura básica
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        context = mockk(relaxed = true)
        viewModel = HomeViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loadStats debería cargar estadísticas`() = runTest {
        // Esperar a que se ejecute loadStats (llamado en init)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val stats = viewModel.stats.value
        assertNotNull(stats)
    }

    @Test
    fun `loadLearnItems debería cargar items`() = runTest {
        // Esperar a que se ejecute loadLearnItems (llamado en init)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val items = viewModel.learnItems.value
        assertNotNull(items)
    }
}
