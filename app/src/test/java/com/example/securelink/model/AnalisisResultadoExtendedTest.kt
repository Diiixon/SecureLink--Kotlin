package com.example.securelink.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias extendidas para el modelo AnalisisResultado
 * Cobertura: Validaciones, estados, diferentes tipos de amenazas
 */
class AnalisisResultadoExtendedTest {

    @Test
    fun `resultado peligroso con Phishing debería tener datos completos`() {
        // Given & When
        val resultado = AnalisisResultado(
            url = "https://fake-paypal.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Phishing",
            imitaA = "PayPal",
            detalles = mapOf(
                "razon" to "Imitación de sitio financiero",
                "nivel" to "alto"
            )
        )

        // Then
        assertEquals("bloqueadas", resultado.peligro)
        assertEquals("Phishing", resultado.tipoAmenaza)
        assertEquals("PayPal", resultado.imitaA)
        assertTrue(resultado.detalles?.isNotEmpty() == true)
    }

    @Test
    fun `resultado con Malware debería identificarse correctamente`() {
        // Given & When
        val resultado = AnalisisResultado(
            url = "https://malicious-site.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Malware",
            imitaA = "N/A",
            detalles = mapOf("tipo" to "virus")
        )

        // Then
        assertEquals("Malware", resultado.tipoAmenaza)
        assertEquals("bloqueadas", resultado.peligro)
    }

    @Test
    fun `resultado con Scam debería tener tipo de amenaza correcto`() {
        // Given & When
        val resultado = AnalisisResultado(
            url = "https://scam-site.com",
            peligro = "sospechosos",
            tipoAmenaza = "Scam",
            imitaA = "N/A",
            detalles = mapOf("advertencia" to "Sitio sospechoso")
        )

        // Then
        assertEquals("Scam", resultado.tipoAmenaza)
        assertEquals("sospechosos", resultado.peligro)
    }

    @Test
    fun `resultado seguro no debería tener sitio imitado`() {
        // Given & When
        val resultado = AnalisisResultado(
            url = "https://legitimate-site.com",
            peligro = "seguros",
            tipoAmenaza = "Ninguno",
            imitaA = "N/A",
            detalles = mapOf()
        )

        // Then
        assertEquals("seguros", resultado.peligro)
        assertEquals("Ninguno", resultado.tipoAmenaza)
        assertTrue(resultado.imitaA == "N/A" || resultado.imitaA.isNullOrBlank())
    }

    @Test
    fun `validar que URL está presente en el resultado`() {
        // Given
        val resultado = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguros",
            tipoAmenaza = "Ninguno",
            imitaA = "N/A",
            detalles = mapOf()
        )

        // When
        val tieneUrl = resultado.url?.isNotBlank() == true

        // Then
        assertTrue("Resultado debería tener URL válida", tieneUrl)
    }

    @Test
    fun `detalles pueden ser un mapa vacío`() {
        // Given & When
        val resultado = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguros",
            tipoAmenaza = "Ninguno",
            imitaA = "N/A",
            detalles = mapOf()
        )

        // Then
        assertNotNull(resultado.detalles)
        assertTrue(resultado.detalles?.isEmpty() == true)
    }

    @Test
    fun `detalles pueden contener múltiples campos`() {
        // Given
        val detalles = mapOf(
            "razon" to "Phishing detectado",
            "nivel" to "alto",
            "categoria" to "financiero",
            "confianza" to "95%"
        )

        // When
        val resultado = AnalisisResultado(
            url = "https://fake-bank.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Phishing",
            imitaA = "Bank of America",
            detalles = detalles
        )

        // Then
        assertEquals(4, resultado.detalles?.size)
        assertEquals("Phishing detectado", resultado.detalles?.get("razon"))
        assertEquals("alto", resultado.detalles?.get("nivel"))
    }

    @Test
    fun `comparar dos resultados por nivel de peligro`() {
        // Given
        val seguro = AnalisisResultado("url1", "seguros", "Ninguno", "N/A", mapOf())
        val peligroso = AnalisisResultado("url2", "bloqueadas", "Phishing", "Google", mapOf())

        // When
        val ordenPeligro = listOf("bloqueadas", "sospechosos", "seguros")
        val indiceSeguro = ordenPeligro.indexOf(seguro.peligro)
        val indicePeligroso = ordenPeligro.indexOf(peligroso.peligro)

        // Then
        assertTrue("Bloqueadas debería tener índice menor (más peligroso)", 
            indicePeligroso < indiceSeguro)
    }

    @Test
    fun `resultado debería poder convertirse a string legible`() {
        // Given
        val resultado = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguros",
            tipoAmenaza = "Ninguno",
            imitaA = "N/A",
            detalles = mapOf()
        )

        // When
        val descripcion = "URL: ${resultado.url}, Peligro: ${resultado.peligro}, Tipo: ${resultado.tipoAmenaza}"

        // Then
        assertTrue(descripcion.contains("https://example.com"))
        assertTrue(descripcion.contains("seguros"))
        assertTrue(descripcion.contains("Ninguno"))
    }

    @Test
    fun `imitaA con valor real debería indicar phishing`() {
        // Given
        val resultado = AnalisisResultado(
            url = "https://fake-site.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Phishing",
            imitaA = "Google",
            detalles = mapOf()
        )

        // When
        val esProbablePhishing = resultado.imitaA != "N/A" && 
                                 resultado.imitaA?.isNotBlank() == true &&
                                 resultado.peligro != "seguros"

        // Then
        assertTrue("Debería ser identificado como probable phishing", esProbablePhishing)
    }
}
