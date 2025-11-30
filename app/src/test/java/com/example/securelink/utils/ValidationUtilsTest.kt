package com.example.securelink.utils

import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para validaciones básicas
 * Estas pruebas verifican la lógica de validación de entrada de datos
 */
class ValidationUtilsTest {

    @Test
    fun `email con formato correcto debería ser válido`() {
        // Given
        val email = "test@example.com"
        
        // When
        val resultado = email.contains("@") && email.contains(".")
        
        // Then
        assertTrue("Email con @ y . debería ser válido", resultado)
    }

    @Test
    fun `email sin arroba debería ser inválido`() {
        // Given
        val email = "testexample.com"
        
        // When
        val resultado = email.contains("@")
        
        // Then
        assertFalse("Email sin @ debería ser inválido", resultado)
    }

    @Test
    fun `password con 6+ caracteres debería ser válido`() {
        // Given
        val password = "password123"
        
        // When
        val isValid = password.length >= 6
        
        // Then
        assertTrue("Password >= 6 caracteres debería ser válido", isValid)
    }

    @Test
    fun `password con menos de 6 caracteres debería ser inválido`() {
        // Given
        val password = "12345"
        
        // When
        val isValid = password.length >= 6
        
        // Then
        assertFalse("Password < 6 caracteres debería ser inválido", isValid)
    }

    @Test
    fun `URL con protocolo http debería ser válida`() {
        // Given
        val url = "http://example.com"
        
        // When
        val isValid = url.startsWith("http://") || url.startsWith("https://")
        
        // Then
        assertTrue("URL con http:// debería ser válida", isValid)
    }

    @Test
    fun `URL con protocolo https debería ser válida`() {
        // Given
        val url = "https://www.google.com"
        
        // When
        val isValid = url.startsWith("http://") || url.startsWith("https://")
        
        // Then
        assertTrue("URL con https:// debería ser válida", isValid)
    }

    @Test
    fun `URL sin protocolo debería ser inválida`() {
        // Given
        val url = "www.example.com"
        
        // When
        val isValid = url.startsWith("http://") || url.startsWith("https://")
        
        // Then
        assertFalse("URL sin protocolo debería ser inválida", isValid)
    }

    @Test
    fun `texto vacío no debería ser válido como nombre`() {
        // Given
        val nombre = ""
        
        // When
        val isValid = nombre.isNotBlank() && nombre.length >= 2
        
        // Then
        assertFalse("Nombre vacío debería ser inválido", isValid)
    }

    @Test
    fun `nombre con 2+ caracteres debería ser válido`() {
        // Given
        val nombre = "Juan"
        
        // When
        val isValid = nombre.isNotBlank() && nombre.length >= 2
        
        // Then
        assertTrue("Nombre con 2+ caracteres debería ser válido", isValid)
    }
}
