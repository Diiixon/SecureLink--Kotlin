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
 * Pruebas unitarias para RegistroViewModel
 * Prueba validaciones y manejo de estado
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegistroViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application
    private lateinit var viewModel: RegistroViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = mockk(relaxed = true)
        viewModel = RegistroViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `estado inicial debería tener campos vacíos`() = runTest {
        val estado = viewModel.estado.first()
        
        assertEquals("", estado.nombre)
        assertEquals("", estado.correo)
        assertEquals("", estado.contrasena)
        assertEquals("", estado.confirmarContrasena)
    }

    @Test
    fun `onNombreChange debería actualizar nombre`() = runTest {
        viewModel.onNombreChange("Juan Pérez")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals("Juan Pérez", estado.nombre)
    }

    @Test
    fun `onCorreoChange debería actualizar correo`() = runTest {
        viewModel.onCorreoChange("test@example.com")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals("test@example.com", estado.correo)
    }

    @Test
    fun `onContrasenaChange debería actualizar contraseña`() = runTest {
        viewModel.onContrasenaChange("password123")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals("password123", estado.contrasena)
    }

    @Test
    fun `onConfirmarContrasenaChange debería actualizar confirmación`() = runTest {
        viewModel.onConfirmarContrasenaChange("password123")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals("password123", estado.confirmarContrasena)
    }

    @Test
    fun `validación de contraseñas coincidentes`() = runTest {
        viewModel.onContrasenaChange("password123")
        viewModel.onConfirmarContrasenaChange("password123")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals(estado.contrasena, estado.confirmarContrasena)
    }

    @Test
    fun `validación de contraseñas no coincidentes`() = runTest {
        viewModel.onContrasenaChange("password123")
        viewModel.onConfirmarContrasenaChange("different")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNotEquals(estado.contrasena, estado.confirmarContrasena)
    }

    @Test
    fun `validación de email con formato correcto`() = runTest {
        viewModel.onCorreoChange("test@example.com")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertTrue(estado.correo.contains("@"))
    }

    @Test
    fun `validación de campos completos`() = runTest {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("pass123")
        viewModel.onConfirmarContrasenaChange("pass123")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertTrue(estado.nombre.isNotBlank())
        assertTrue(estado.correo.isNotBlank())
        assertTrue(estado.contrasena.isNotBlank())
        assertTrue(estado.confirmarContrasena.isNotBlank())
    }

    @Test
    fun `validación de nombre vacío`() = runTest {
        viewModel.onNombreChange("")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertTrue(estado.nombre.isBlank())
    }

    @Test
    fun `validación de contraseña vacía`() = runTest {
        viewModel.onContrasenaChange("")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertTrue(estado.contrasena.isBlank())
    }

    @Test
    fun `múltiples cambios en nombre`() = runTest {
        viewModel.onNombreChange("Juan")
        viewModel.onNombreChange("Pedro")
        viewModel.onNombreChange("Carlos")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals("Carlos", estado.nombre)
    }

    @Test
    fun `múltiples cambios en correo`() = runTest {
        viewModel.onCorreoChange("juan@test.com")
        viewModel.onCorreoChange("pedro@test.com")
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertEquals("pedro@test.com", estado.correo)
    }

    @Test
    fun `registrarUsuario con nombre vacío debe mostrar error`() = runTest {
        viewModel.onNombreChange("")
        viewModel.onCorreoChange("test@test.com")
        viewModel.onContrasenaChange("password123")
        viewModel.onConfirmarContrasenaChange("password123")
        
        viewModel.registrarUsuario {}
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNotNull(estado.error)
    }

    @Test
    fun `registrarUsuario con contraseñas no coincidentes debe mostrar error`() = runTest {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("juan@test.com")
        viewModel.onContrasenaChange("password123")
        viewModel.onConfirmarContrasenaChange("differentpass")
        
        viewModel.registrarUsuario {}
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNotNull(estado.error)
    }

    @Test
    fun `registrarUsuario con contraseña corta debe mostrar error`() = runTest {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("juan@test.com")
        viewModel.onContrasenaChange("123")
        viewModel.onConfirmarContrasenaChange("123")
        
        viewModel.registrarUsuario {}
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNotNull(estado.error)
    }

    @Test
    fun `registrarUsuario con email sin arroba debe mostrar error`() = runTest {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("emailinvalido")
        viewModel.onContrasenaChange("password123")
        viewModel.onConfirmarContrasenaChange("password123")
        
        viewModel.registrarUsuario {}
        testDispatcher.scheduler.advanceUntilIdle()
        
        val estado = viewModel.estado.first()
        assertNotNull(estado.error)
    }

    @Test
    fun `clearError debe limpiar el error del estado`() = runTest {
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertNull(estado.error)
    }

    @Test
    fun `onNombreChange con nombre largo actualiza correctamente`() = runTest {
        val nombreLargo = "Juan Carlos Alberto Pérez García López"
        viewModel.onNombreChange(nombreLargo)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(nombreLargo, viewModel.estado.value.nombre)
    }

    @Test
    fun `onCorreoChange con correo complejo actualiza correctamente`() = runTest {
        val correo = "user.name+tag@subdomain.example.com"
        viewModel.onCorreoChange(correo)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(correo, viewModel.estado.value.correo)
    }

    @Test
    fun `onContrasenaChange con contraseña compleja actualiza correctamente`() = runTest {
        val passCompleja = "P@ssw0rd!2024#Secure"
        viewModel.onContrasenaChange(passCompleja)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(passCompleja, viewModel.estado.value.contrasena)
    }

    @Test
    fun `onConfirmarContrasenaChange actualiza confirmación`() = runTest {
        viewModel.onConfirmarContrasenaChange("confirmar123")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("confirmar123", viewModel.estado.value.confirmarContrasena)
    }

    @Test
    fun `múltiples llamadas a clearError mantienen estado sin error`() = runTest {
        viewModel.clearError()
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.estado.value.error)
    }

    @Test
    fun `registrarUsuario con correo vacío debe mostrar error de validación`() = runTest {
        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("")
        viewModel.onContrasenaChange("pass123")
        viewModel.onConfirmarContrasenaChange("pass123")
        
        viewModel.registrarUsuario {}
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertNotNull(viewModel.estado.value.error)
    }

    @Test
    fun `registrarUsuario con todos los campos vacíos debe mostrar error`() = runTest {
        viewModel.registrarUsuario {}
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertNotNull(viewModel.estado.value.error)
    }

    @Test
    fun `todos los onChange limpian el error`() = runTest {
        viewModel.onNombreChange("Test")
        testDispatcher.scheduler.advanceUntilIdle()
        assertNull(viewModel.estado.value.error)

        viewModel.onCorreoChange("test@test.com")
        testDispatcher.scheduler.advanceUntilIdle()
        assertNull(viewModel.estado.value.error)

        viewModel.onContrasenaChange("pass")
        testDispatcher.scheduler.advanceUntilIdle()
        assertNull(viewModel.estado.value.error)

        viewModel.onConfirmarContrasenaChange("pass")
        testDispatcher.scheduler.advanceUntilIdle()
        assertNull(viewModel.estado.value.error)
    }
}
