package com.example.securelink.model

import org.junit.Assert.assertEquals
import org.junit.Test

class LoginRequestTest {

    @Test
    fun `crearLoginRequest_deberiaGuardarEmailYPassword`() {
        // Given
        val email = "test@example.com"
        val password = "password123"

        // When
        val request = LoginRequest(email = email, password = password)

        // Then
        assertEquals(email, request.email)
        assertEquals(password, request.password)
    }
}
