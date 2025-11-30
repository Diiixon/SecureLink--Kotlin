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
 * Pruebas unitarias para EstadisticasViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class EstadisticasViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    private lateinit var viewModel: EstadisticasViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        context = mockk(relaxed = true)
        viewModel = EstadisticasViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `uiState debería tener datos de amenazas inicializados`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        val estado = viewModel.uiState.value
        assertNotNull(estado.amenazasUsuario)
    }

    @Test
    fun `uiState debería tener datos de comparativa inicializados`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        val estado = viewModel.uiState.value
        assertNotNull(estado.comparativaData)
    }
}
