package com.example.securelink.model.Data

import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para Usuario
 */
class UsuarioTest {

    @Test
    fun `Usuario debería crearse con todos los campos`() {
        val usuario = Usuario(
            id = "1",
            nombre = "Juan Pérez",
            correo = "juan@example.com"
        )
        
        assertEquals("1", usuario.id)
        assertEquals("Juan Pérez", usuario.nombre)
        assertEquals("juan@example.com", usuario.correo)
    }

    @Test
    fun `Usuario debería tener correo válido`() {
        val usuario = Usuario(
            id = "1",
            nombre = "Test",
            correo = "test@domain.com"
        )
        
        assertTrue(usuario.correo.contains("@"))
        assertTrue(usuario.correo.isNotBlank())
    }

    @Test
    fun `Usuario con campos vacíos`() {
        val usuario = Usuario(
            id = "",
            nombre = "",
            correo = ""
        )
        
        assertEquals("", usuario.id)
        assertEquals("", usuario.nombre)
        assertEquals("", usuario.correo)
    }

    @Test
    fun `dos Usuarios con mismo id deberían considerarse iguales`() {
        val usuario1 = Usuario("1", "User1", "email1@test.com")
        val usuario2 = Usuario("1", "User1", "email1@test.com")
        
        assertEquals(usuario1, usuario2)
    }

    @Test
    fun `Usuario con nombre válido`() {
        val usuario = Usuario("1", "María García", "maria@test.com")
        
        assertTrue(usuario.nombre.isNotBlank())
        assertTrue(usuario.nombre.length > 2)
    }

    @Test
    fun `Usuario copy debe crear nueva instancia`() {
        val original = Usuario("user123", "Juan", "juan@example.com")
        val copy = original.copy()

        assertEquals(original.id, copy.id)
        assertEquals(original.nombre, copy.nombre)
    }

    @Test
    fun `Usuario copy con cambio de nombre`() {
        val original = Usuario("user123", "Juan", "juan@example.com")
        val copy = original.copy(nombre = "Pedro")

        assertEquals("Pedro", copy.nombre)
        assertEquals(original.id, copy.id)
    }

    @Test
    fun `Usuario hashCode debe ser igual para objetos iguales`() {
        val usuario1 = Usuario("user123", "Juan", "juan@example.com")
        val usuario2 = Usuario("user123", "Juan", "juan@example.com")

        assertEquals(usuario1.hashCode(), usuario2.hashCode())
    }

    @Test
    fun `Usuario toString debe contener información relevante`() {
        val usuario = Usuario("user123", "Juan", "juan@example.com")
        val string = usuario.toString()

        assertTrue(string.contains("user123"))
        assertTrue(string.contains("Juan"))
    }

    @Test
    fun `Usuario copy con múltiples cambios`() {
        val original = Usuario("user123", "Juan", "juan@example.com")
        val copy = original.copy(
            id = "user456",
            nombre = "Pedro",
            correo = "pedro@example.com"
        )

        assertEquals("user456", copy.id)
        assertEquals("Pedro", copy.nombre)
        assertEquals("pedro@example.com", copy.correo)
    }
}
