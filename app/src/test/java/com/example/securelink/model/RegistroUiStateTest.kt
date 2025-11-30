package com.example.securelink.model

import org.junit.Assert.*
import org.junit.Test

class RegistroUiStateTest {

    @Test
    fun `RegistroUiState con valores por defecto debe construirse correctamente`() {
        val state = RegistroUiState()

        assertEquals("", state.nombre)
        assertEquals("", state.correo)
        assertEquals("", state.contrasena)
        assertEquals("", state.confirmarContrasena)
        assertNull(state.error)
        assertFalse(state.registroExitoso)
        assertFalse(state.isLoading)
    }

    @Test
    fun `RegistroUiState con todos los campos debe construirse correctamente`() {
        val state = RegistroUiState(
            nombre = "Juan Pérez",
            correo = "juan@example.com",
            contrasena = "password123",
            confirmarContrasena = "password123",
            error = "Error de validación",
            registroExitoso = false,
            isLoading = true
        )

        assertEquals("Juan Pérez", state.nombre)
        assertEquals("juan@example.com", state.correo)
        assertEquals("password123", state.contrasena)
        assertEquals("password123", state.confirmarContrasena)
        assertEquals("Error de validación", state.error)
        assertFalse(state.registroExitoso)
        assertTrue(state.isLoading)
    }

    @Test
    fun `RegistroUiState copy debe crear nueva instancia`() {
        val original = RegistroUiState(nombre = "Juan", correo = "juan@example.com")
        val copy = original.copy()

        assertEquals(original.nombre, copy.nombre)
        assertEquals(original.correo, copy.correo)
    }

    @Test
    fun `RegistroUiState copy con cambio de nombre`() {
        val original = RegistroUiState(nombre = "Juan")
        val copy = original.copy(nombre = "Pedro")

        assertEquals("Pedro", copy.nombre)
    }

    @Test
    fun `RegistroUiState copy con cambio de correo`() {
        val original = RegistroUiState(correo = "juan@example.com")
        val copy = original.copy(correo = "pedro@example.com")

        assertEquals("pedro@example.com", copy.correo)
    }

    @Test
    fun `RegistroUiState copy con cambio de contrasena`() {
        val original = RegistroUiState(contrasena = "oldpass")
        val copy = original.copy(contrasena = "newpass")

        assertEquals("newpass", copy.contrasena)
    }

    @Test
    fun `RegistroUiState copy con cambio de confirmarContrasena`() {
        val original = RegistroUiState(confirmarContrasena = "oldpass")
        val copy = original.copy(confirmarContrasena = "newpass")

        assertEquals("newpass", copy.confirmarContrasena)
    }

    @Test
    fun `RegistroUiState copy con cambio de error`() {
        val original = RegistroUiState(error = null)
        val copy = original.copy(error = "Error de registro")

        assertEquals("Error de registro", copy.error)
    }

    @Test
    fun `RegistroUiState copy con cambio de registroExitoso`() {
        val original = RegistroUiState(registroExitoso = false)
        val copy = original.copy(registroExitoso = true)

        assertTrue(copy.registroExitoso)
    }

    @Test
    fun `RegistroUiState copy con cambio de isLoading`() {
        val original = RegistroUiState(isLoading = false)
        val copy = original.copy(isLoading = true)

        assertTrue(copy.isLoading)
    }

    @Test
    fun `RegistroUiState equals debe retornar true para objetos iguales`() {
        val state1 = RegistroUiState("Juan", "juan@example.com", "pass123", "pass123")
        val state2 = RegistroUiState("Juan", "juan@example.com", "pass123", "pass123")

        assertEquals(state1, state2)
    }

    @Test
    fun `RegistroUiState hashCode debe ser igual para objetos iguales`() {
        val state1 = RegistroUiState("Juan", "juan@example.com", "pass123", "pass123")
        val state2 = RegistroUiState("Juan", "juan@example.com", "pass123", "pass123")

        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `RegistroUiState toString debe contener información relevante`() {
        val state = RegistroUiState("Juan", "juan@example.com")
        val string = state.toString()

        assertTrue(string.contains("Juan"))
        assertTrue(string.contains("juan@example.com"))
    }

    @Test
    fun `RegistroUiState copy con múltiples cambios`() {
        val original = RegistroUiState()
        val copy = original.copy(
            nombre = "Juan",
            correo = "juan@example.com",
            contrasena = "pass123",
            confirmarContrasena = "pass123",
            registroExitoso = true
        )

        assertEquals("Juan", copy.nombre)
        assertEquals("juan@example.com", copy.correo)
        assertEquals("pass123", copy.contrasena)
        assertEquals("pass123", copy.confirmarContrasena)
        assertTrue(copy.registroExitoso)
    }
}
