package com.example.securelink.model

import org.junit.Assert.*
import org.junit.Test

class RecuperarUiStateTest {

    @Test
    fun `RecuperarUiState con valores por defecto debe construirse correctamente`() {
        val state = RecuperarUiState()

        assertEquals("", state.correoElectronico)
        assertNull(state.error)
        assertFalse(state.enlaceEnviado)
        assertFalse(state.isLoading)
    }

    @Test
    fun `RecuperarUiState con todos los campos debe construirse correctamente`() {
        val state = RecuperarUiState(
            correoElectronico = "test@example.com",
            error = "Error al enviar",
            enlaceEnviado = true,
            isLoading = false
        )

        assertEquals("test@example.com", state.correoElectronico)
        assertEquals("Error al enviar", state.error)
        assertTrue(state.enlaceEnviado)
        assertFalse(state.isLoading)
    }

    @Test
    fun `RecuperarUiState copy debe crear nueva instancia`() {
        val original = RecuperarUiState(correoElectronico = "test@example.com")
        val copy = original.copy()

        assertEquals(original.correoElectronico, copy.correoElectronico)
    }

    @Test
    fun `RecuperarUiState copy con cambio de correoElectronico`() {
        val original = RecuperarUiState(correoElectronico = "test@example.com")
        val copy = original.copy(correoElectronico = "new@example.com")

        assertEquals("new@example.com", copy.correoElectronico)
    }

    @Test
    fun `RecuperarUiState copy con cambio de error`() {
        val original = RecuperarUiState(error = null)
        val copy = original.copy(error = "Error de validación")

        assertEquals("Error de validación", copy.error)
    }

    @Test
    fun `RecuperarUiState copy con cambio de enlaceEnviado`() {
        val original = RecuperarUiState(enlaceEnviado = false)
        val copy = original.copy(enlaceEnviado = true)

        assertTrue(copy.enlaceEnviado)
    }

    @Test
    fun `RecuperarUiState copy con cambio de isLoading`() {
        val original = RecuperarUiState(isLoading = false)
        val copy = original.copy(isLoading = true)

        assertTrue(copy.isLoading)
    }

    @Test
    fun `RecuperarUiState equals debe retornar true para objetos iguales`() {
        val state1 = RecuperarUiState("test@example.com", null, false, false)
        val state2 = RecuperarUiState("test@example.com", null, false, false)

        assertEquals(state1, state2)
    }

    @Test
    fun `RecuperarUiState hashCode debe ser igual para objetos iguales`() {
        val state1 = RecuperarUiState("test@example.com", null, false, false)
        val state2 = RecuperarUiState("test@example.com", null, false, false)

        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `RecuperarUiState toString debe contener información relevante`() {
        val state = RecuperarUiState("test@example.com")
        val string = state.toString()

        assertTrue(string.contains("test@example.com"))
    }

    @Test
    fun `RecuperarUiState copy con múltiples cambios`() {
        val original = RecuperarUiState()
        val copy = original.copy(
            correoElectronico = "test@example.com",
            enlaceEnviado = true,
            isLoading = false
        )

        assertEquals("test@example.com", copy.correoElectronico)
        assertTrue(copy.enlaceEnviado)
        assertFalse(copy.isLoading)
    }

    @Test
    fun `RecuperarUiState con correo vacío es válido`() {
        val state = RecuperarUiState(correoElectronico = "")

        assertEquals("", state.correoElectronico)
    }

    @Test
    fun `RecuperarUiState con error limpia enlaceEnviado`() {
        val state = RecuperarUiState(
            error = "Error al enviar",
            enlaceEnviado = false
        )

        assertNotNull(state.error)
        assertFalse(state.enlaceEnviado)
    }
}
