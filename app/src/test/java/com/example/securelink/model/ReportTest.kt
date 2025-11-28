package com.example.securelink.model

import org.junit.Test
import org.junit.Assert.*

class ReportTest {

    @Test
    fun `Report con peligro bloqueadas debería ser considerado peligroso`() {
        // Given
        val report = Report(
            id = 1,
            url = "https://phishing.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Phishing",
            createdAt = "2025-11-22T01:00:00Z",
            detalles = "{}",
            imitaA = "Google",
            userId = 1
        )
        
        // Then
        assertEquals("bloqueadas", report.peligro)
        assertEquals("Phishing", report.tipoAmenaza)
        assertEquals("Google", report.imitaA)
    }

    @Test
    fun `Report con peligro seguros debería tener tipo de amenaza Ninguno`() {
        // Given
        val report = Report(
            id = 1,
            url = "https://safe-site.com",
            peligro = "seguros",
            tipoAmenaza = "Ninguno",
            createdAt = "2025-11-22T01:00:00Z",
            detalles = "{}",
            imitaA = "N/A",
            userId = 1
        )
        
        // Then
        assertEquals("seguros", report.peligro)
        assertEquals("Ninguno", report.tipoAmenaza)
        assertEquals("N/A", report.imitaA)
    }

    @Test
    fun `Report con peligro sospechosos debería mantener el tipo correcto`() {
        // Given
        val report = Report(
            id = 1,
            url = "https://suspicious-site.com",
            peligro = "sospechosos",
            tipoAmenaza = "Scam",
            createdAt = "2025-11-22T01:00:00Z",
            detalles = "{}",
            imitaA = "Sitio Desconocido",
            userId = 1
        )
        
        // Then
        assertEquals("sospechosos", report.peligro)
        assertEquals("Scam", report.tipoAmenaza)
    }

    @Test
    fun `Report debería poder tener campos opcionales como null`() {
        // Given
        val report = Report(
            id = 1,
            url = "https://example.com",
            peligro = null,
            tipoAmenaza = null,
            createdAt = null,
            detalles = null,
            imitaA = null,
            userId = null
        )
        
        // Then
        assertNull(report.peligro)
        assertNull(report.tipoAmenaza)
        assertNull(report.imitaA)
    }

    @Test
    fun `Report copy debe crear nueva instancia`() {
        val original = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            createdAt = "2024-01-01",
            detalles = "Sin problemas",
            imitaA = null,
            userId = 123
        )

        val copy = original.copy()

        assertEquals(original.id, copy.id)
        assertEquals(original.url, copy.url)
        assertEquals(original.peligro, copy.peligro)
    }

    @Test
    fun `Report copy con cambio de url`() {
        val original = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            createdAt = null,
            detalles = null,
            imitaA = null,
            userId = null
        )

        val copy = original.copy(url = "https://changed.com")

        assertEquals("https://changed.com", copy.url)
        assertEquals(original.id, copy.id)
    }

    @Test
    fun `Report equals debe retornar true para objetos iguales`() {
        val report1 = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            createdAt = "2024-01-01",
            detalles = "Test",
            imitaA = null,
            userId = 123
        )

        val report2 = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            createdAt = "2024-01-01",
            detalles = "Test",
            imitaA = null,
            userId = 123
        )

        assertEquals(report1, report2)
    }

    @Test
    fun `Report hashCode debe ser igual para objetos iguales`() {
        val report1 = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            createdAt = null,
            detalles = null,
            imitaA = null,
            userId = null
        )

        val report2 = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            createdAt = null,
            detalles = null,
            imitaA = null,
            userId = null
        )

        assertEquals(report1.hashCode(), report2.hashCode())
    }

    @Test
    fun `Report toString debe contener información relevante`() {
        val report = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            createdAt = "2024-01-01",
            detalles = "Test",
            imitaA = null,
            userId = 123
        )

        val string = report.toString()

        assertTrue(string.contains("1"))
        assertTrue(string.contains("https://example.com"))
    }

    @Test
    fun `Report copy con múltiples cambios`() {
        val original = Report(
            id = 1,
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            createdAt = null,
            detalles = null,
            imitaA = null,
            userId = null
        )

        val copy = original.copy(
            url = "https://changed.com",
            peligro = "peligroso",
            tipoAmenaza = "Phishing"
        )

        assertEquals("https://changed.com", copy.url)
        assertEquals("peligroso", copy.peligro)
        assertEquals("Phishing", copy.tipoAmenaza)
    }
}
