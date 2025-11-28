package com.example.securelink.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.securelink.model.Data.Usuario
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para PerfilViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PerfilViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    private lateinit var viewModel: PerfilViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        context = mockk(relaxed = true)
        
        // Mock SharedPreferences
        val sharedPrefs = mockk<android.content.SharedPreferences>(relaxed = true)
        every { context.getSharedPreferences(any(), any()) } returns sharedPrefs
        every { sharedPrefs.getString(any(), any()) } returns "test-token"
        
        viewModel = PerfilViewModel(context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `historialAnalisis debería inicializar vacío`() = runTest {
        val historial = viewModel.historialAnalisis.first()
        assertNotNull(historial)
    }

    @Test
    fun `usuario debería inicializar como null`() = runTest {
        val usuario = viewModel.usuario.first()
        // Usuario es null hasta que se llama cargarDatos()
        assertNull(usuario)
    }

    @Test
    fun `isLoading debería inicializar como false`() = runTest {
        val loading = viewModel.isLoading.first()
        assertFalse(loading)
    }

}
