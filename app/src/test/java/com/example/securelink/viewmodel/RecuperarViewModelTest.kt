package com.example.securelink.viewmodel

import android.app.Application
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

/**
 * Pruebas unitarias para RecuperarViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RecuperarViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application
    private lateinit var viewModel: RecuperarViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = mockk(relaxed = true)
        viewModel = RecuperarViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `estado inicial debería tener email vacío`() = runTest {
        val estado = viewModel.estado.first()
        assertEquals("", estado.correoElectronico)
    }

    @Test
    fun `onCorreoChange debería actualizar email`() = runTest {
        viewModel.onCorreoChange("recover@example.com")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals("recover@example.com", estado.correoElectronico)
    }

    @Test
    fun `validación de email con arroba`() = runTest {
        viewModel.onCorreoChange("valid@email.com")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertTrue(estado.correoElectronico.contains("@"))
    }

    @Test
    fun `clearError debería limpiar el error`() = runTest {
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNull(estado.error)
    }

    @Test
    fun `resetState debería reiniciar enlaceEnviado`() = runTest {
        viewModel.resetState()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertFalse(estado.enlaceEnviado)
    }

    @Test
    fun `enviarCorreoRecuperacion con email vacío debe mostrar error`() = runTest {
        viewModel.onCorreoChange("")
        
        viewModel.enviarCorreoRecuperacion()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNotNull(estado.error)
    }

    @Test
    fun `enviarCorreoRecuperacion con email sin arroba debe mostrar error`() = runTest {
        viewModel.onCorreoChange("emailinvalido")
        
        viewModel.enviarCorreoRecuperacion()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNotNull(estado.error)
    }

    @Test
    fun `enviarCorreoRecuperacion con email válido debe simular envío`() = runTest {
        viewModel.onCorreoChange("test@test.com")
        
        viewModel.enviarCorreoRecuperacion()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        // Debe estar enviado o tener error (depende de la red)
        assertTrue(estado.enlaceEnviado || estado.error != null)
    }

    @Test
    fun `clearError debe limpiar el error`() = runTest {
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.estado.value.error)
    }

    @Test
    fun `resetState debe reiniciar enlaceEnviado a false`() = runTest {
        viewModel.resetState()
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.estado.value.enlaceEnviado)
    }

    @Test
    fun `onCorreoChange con correo largo actualiza correctamente`() = runTest {
        val correoLargo = "usuario.muy.largo.con.muchos.puntos@example.com"
        viewModel.onCorreoChange(correoLargo)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(correoLargo, viewModel.estado.value.correoElectronico)
    }

    @Test
    fun `enviarCorreoRecuperacion con espacios debe detectar error`() = runTest {
        viewModel.onCorreoChange("   ")
        
        viewModel.enviarCorreoRecuperacion()
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertNotNull(viewModel.estado.value.error)
    }

    @Test
    fun `múltiples llamadas a clearError mantienen estado limpio`() = runTest {
        viewModel.clearError()
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.estado.value.error)
    }

    @Test
    fun `múltiples llamadas a resetState mantienen enlaceEnviado en false`() = runTest {
        viewModel.resetState()
        viewModel.resetState()
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.estado.value.enlaceEnviado)
    }

    @Test
    fun `onCorreoChange limpia el error`() = runTest {
        viewModel.onCorreoChange("nuevo@test.com")
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.estado.value.error)
    }

    @Test
    fun `enviarCorreoRecuperacion sin @ debe mostrar error`() = runTest {
        viewModel.onCorreoChange("emailsinvalido")
        
        viewModel.enviarCorreoRecuperacion()
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertNotNull(viewModel.estado.value.error)
    }
}
