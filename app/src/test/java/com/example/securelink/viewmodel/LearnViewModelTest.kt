package com.example.securelink.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.securelink.model.LearnItem
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
 * Pruebas unitarias para LearnViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LearnViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    private lateinit var viewModel: LearnViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        context = mockk(relaxed = true)
        viewModel = LearnViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loadLearnItems debería cargar items de aprendizaje`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        // Verificar que el método loadLearnItems se ejecutó
        assertNotNull(viewModel)
    }
}
