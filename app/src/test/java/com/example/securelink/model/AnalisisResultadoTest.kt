package com.example.securelink.model

import org.junit.Test
import org.junit.Assert.*

class AnalisisResultadoTest {

    @Test
    fun `AnalisisResultado con URL peligrosa debería tener datos completos`() {
        // Given
        val resultado = AnalisisResultado(
            url = "https://phishing-site.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Phishing",
            imitaA = "PayPal",
            detalles = mapOf(
                "GoogleSafeBrowsing" to "MALWARE",
                "VirusTotal" to "5/90 malicious"
            )
        )
        
        // Then
        assertEquals("bloqueadas", resultado.peligro)
        assertEquals("Phishing", resultado.tipoAmenaza)
        assertEquals("PayPal", resultado.imitaA)
        assertNotNull(resultado.detalles)
        assertEquals(2, resultado.detalles?.size)
    }

    @Test
    fun `AnalisisResultado con URL segura debería tener tipo Ninguno`() {
        // Given
        val resultado = AnalisisResultado(
            url = "https://google.com",
            peligro = "seguros",
            tipoAmenaza = "Ninguno",
            imitaA = "N/A",
            detalles = mapOf(
                "GoogleSafeBrowsing" to "SAFE",
                "VirusTotal" to "0/90 malicious"
            )
        )
        
        // Then
        assertEquals("seguros", resultado.peligro)
        assertEquals("Ninguno", resultado.tipoAmenaza)
        assertEquals("N/A", resultado.imitaA)
    }

    @Test
    fun `AnalisisResultado debería aceptar detalles opcionales`() {
        // Given
        val resultado = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguros",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
        )
        
        // Then
        assertNull(resultado.tipoAmenaza)
        assertNull(resultado.imitaA)
        assertNull(resultado.detalles)
    }

    @Test
    fun `AnalisisResultado copy debe crear nueva instancia`() {
        val original = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            imitaA = null,
            detalles = mapOf("test" to "value")
        )

        val copy = original.copy()

        assertEquals(original.url, copy.url)
        assertEquals(original.peligro, copy.peligro)
    }

    @Test
    fun `AnalisisResultado copy con cambio de url`() {
        val original = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
        )

        val copy = original.copy(url = "https://changed.com")

        assertEquals("https://changed.com", copy.url)
        assertEquals(original.peligro, copy.peligro)
    }

    @Test
    fun `AnalisisResultado equals debe retornar true para objetos iguales`() {
        val resultado1 = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            imitaA = null,
            detalles = mapOf("test" to "value")
        )

        val resultado2 = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            imitaA = null,
            detalles = mapOf("test" to "value")
        )

        assertEquals(resultado1, resultado2)
    }

    @Test
    fun `AnalisisResultado hashCode debe ser igual para objetos iguales`() {
        val resultado1 = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
        )

        val resultado2 = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
        )

        assertEquals(resultado1.hashCode(), resultado2.hashCode())
    }

    @Test
    fun `AnalisisResultado toString debe contener información relevante`() {
        val resultado = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = "Ninguna",
            imitaA = null,
            detalles = mapOf("test" to "value")
        )

        val string = resultado.toString()

        assertTrue(string.contains("https://example.com"))
        assertTrue(string.contains("seguro"))
    }

    @Test
    fun `AnalisisResultado detalles con múltiples entries`() {
        val detalles = mapOf(
            "ssl" to "Válido",
            "reputacion" to "Buena",
            "edad" to "5 años",
            "certificado" to "Activo"
        )

        val resultado = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            imitaA = null,
            detalles = detalles
        )

        assertEquals(4, resultado.detalles?.size)
        assertEquals("Válido", resultado.detalles?.get("ssl"))
    }

    @Test
    fun `AnalisisResultado copy con múltiples cambios`() {
        val original = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguro",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
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
