package com.example.securelink.model

import org.junit.Assert.*
import org.junit.Test

class LoginUiStateTest {

    @Test
    fun `LoginUiState con valores por defecto debe construirse correctamente`() {
        val state = LoginUiState()

        assertEquals("", state.correoElectronico)
        assertEquals("", state.contrasena)
        assertFalse(state.cargando)
        assertNull(state.error)
        assertNull(state.mensajeError)
        assertFalse(state.sesionIniciada)
    }

    @Test
    fun `LoginUiState con todos los campos debe construirse correctamente`() {
        val state = LoginUiState(
            correoElectronico = "test@example.com",
            contrasena = "password123",
            cargando = true,
            error = "Error de red",
            mensajeError = "No se pudo conectar",
            sesionIniciada = false
        )

        assertEquals("test@example.com", state.correoElectronico)
        assertEquals("password123", state.contrasena)
        assertTrue(state.cargando)
        assertEquals("Error de red", state.error)
        assertEquals("No se pudo conectar", state.mensajeError)
        assertFalse(state.sesionIniciada)
    }

    @Test
    fun `LoginUiState copy debe crear nueva instancia`() {
        val original = LoginUiState(
            correoElectronico = "test@example.com",
            contrasena = "password123"
        )
        val copy = original.copy()

        assertEquals(original.correoElectronico, copy.correoElectronico)
        assertEquals(original.contrasena, copy.contrasena)
    }

    @Test
    fun `LoginUiState copy con cambio de correoElectronico`() {
        val original = LoginUiState(correoElectronico = "test@example.com")
        val copy = original.copy(correoElectronico = "new@example.com")

        assertEquals("new@example.com", copy.correoElectronico)
    }

    @Test
    fun `LoginUiState copy con cambio de contrasena`() {
        val original = LoginUiState(contrasena = "oldpass")
        val copy = original.copy(contrasena = "newpass")

        assertEquals("newpass", copy.contrasena)
    }

    @Test
    fun `LoginUiState copy con cambio de cargando`() {
        val original = LoginUiState(cargando = false)
        val copy = original.copy(cargando = true)

        assertTrue(copy.cargando)
    }

    @Test
    fun `LoginUiState copy con cambio de error`() {
        val original = LoginUiState(error = null)
        val copy = original.copy(error = "Error de autenticación")

        assertEquals("Error de autenticación", copy.error)
    }

    @Test
    fun `LoginUiState copy con cambio de sesionIniciada`() {
        val original = LoginUiState(sesionIniciada = false)
        val copy = original.copy(sesionIniciada = true)

        assertTrue(copy.sesionIniciada)
    }

    @Test
    fun `LoginUiState equals debe retornar true para objetos iguales`() {
        val state1 = LoginUiState("test@example.com", "pass123")
        val state2 = LoginUiState("test@example.com", "pass123")

        assertEquals(state1, state2)
    }

    @Test
    fun `LoginUiState hashCode debe ser igual para objetos iguales`() {
        val state1 = LoginUiState("test@example.com", "pass123")
        val state2 = LoginUiState("test@example.com", "pass123")

        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `LoginUiState toString debe contener información relevante`() {
        val state = LoginUiState("test@example.com", "pass123")
        val string = state.toString()

        assertTrue(string.contains("test@example.com"))
    }

    @Test
    fun `LoginUiState copy con múltiples cambios`() {
        val original = LoginUiState()
        val copy = original.copy(
            correoElectronico = "test@example.com",
            contrasena = "pass123",
            cargando = true,
            sesionIniciada = false
        )

        assertEquals("test@example.com", copy.correoElectronico)
        assertEquals("pass123", copy.contrasena)
        assertTrue(copy.cargando)
        assertFalse(copy.sesionIniciada)
    }
}
