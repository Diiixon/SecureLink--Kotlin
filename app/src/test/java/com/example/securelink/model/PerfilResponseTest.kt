package com.example.securelink.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PerfilResponseTest {

    @Test
    fun `crearPerfilResponse_deberiaContenerUsuarioEsperado`() {
        // Given
        val id = 1L
        val name = "Juan Perez"
        val email = "juan.perez@example.com"
        val createdAt = "2024-01-01T10:00:00Z"

        // When
        val perfil = PerfilResponse(
            id = id,
            name = name,
            email = email,
            createdAt = createdAt
        )

        // Then
        assertEquals(id, perfil.id)
        assertEquals(name, perfil.name)
        assertEquals(email, perfil.email)
        assertEquals(createdAt, perfil.createdAt)
    }
}
