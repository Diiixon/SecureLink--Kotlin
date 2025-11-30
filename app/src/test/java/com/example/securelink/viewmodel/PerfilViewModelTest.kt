package com.example.securelink.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

@OptIn(ExperimentalCoroutinesApi::class)
class PerfilViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    private lateinit var viewModel: PerfilViewModel
    private lateinit var sharedPrefs: SharedPreferences

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        context = mockk(relaxed = true)
        sharedPrefs = mockk(relaxed = true)

        // Arreglo de tipos explícitos
        every { context.getSharedPreferences(any<String>(), any<Int>()) } returns sharedPrefs
        // Pasamos null literal como valor por defecto para getString
        every { sharedPrefs.getString(any<String>(), null) } returns "test-token"

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
        // La lista vacía no es null, es una lista de tamaño 0
        assertNotNull(historial)
        assertTrue(historial.isEmpty())
    }

    @Test
    fun `usuario debería inicializar como null`() = runTest {
        val usuario = viewModel.usuario.first()
        assertNull(usuario)
    }

    @Test
    fun `isLoading debería inicializar como false`() = runTest {
        val loading = viewModel.isLoading.first()
        assertFalse(loading)
    }
}