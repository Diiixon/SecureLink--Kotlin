package com.example.securelink.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ReporteResponseTest {

    @Test
    fun `crearReporteResponse_deberiaContenerDatosCorrectos`() {
        // Given
        val id = 10
        val url = "https://malicious.example.com"
        val tipoAmenaza = "Phishing"
        val peligro = "Alto"
        val imitaA = "Banco X"
        val createdAt = "2024-01-01T12:00:00Z"

        // When
        val response = ReporteResponse(
            id = id,
            url = url,
            tipoAmenaza = tipoAmenaza,
            peligro = peligro,
            imitaA = imitaA,
            createdAt = createdAt
        )

        // Then
        assertEquals(id, response.id)
        assertEquals(url, response.url)
        assertEquals(tipoAmenaza, response.tipoAmenaza)
        assertEquals(peligro, response.peligro)
        assertEquals(imitaA, response.imitaA)
        assertEquals(createdAt, response.createdAt)
    }
}
