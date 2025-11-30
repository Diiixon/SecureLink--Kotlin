package com.example.securelink.repository

import com.example.securelink.model.Report
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias extendidas para lógica de reportes
 * Cobertura: Estadísticas, filtrado, agrupación, validaciones
 */
class ReportesRepositoryExtendedTest {

    @Test
    fun `calcular porcentaje de URLs seguras`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "seguros", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "seguros", "Ninguno", "2025-11-22T02:00:00Z", "{}", "N/A", 1),
            Report(3, "url3", "seguros", "Ninguno", "2025-11-22T03:00:00Z", "{}", "N/A", 1),
            Report(4, "url4", "bloqueadas", "Phishing", "2025-11-22T04:00:00Z", "{}", "Google", 1)
        )

        // When
        val seguros = reportes.count { it.peligro == "seguros" }
        val porcentaje = (seguros.toFloat() / reportes.size) * 100

        // Then
        assertEquals(75.0f, porcentaje, 0.1f)
    }

    @Test
    fun `agrupar reportes por tipo de amenaza`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "bloqueadas", "Phishing", "2025-11-22T01:00:00Z", "{}", "Google", 1),
            Report(2, "url2", "bloqueadas", "Phishing", "2025-11-22T02:00:00Z", "{}", "PayPal", 1),
            Report(3, "url3", "bloqueadas", "Malware", "2025-11-22T03:00:00Z", "{}", "N/A", 1),
            Report(4, "url4", "sospechosos", "Scam", "2025-11-22T04:00:00Z", "{}", "N/A", 1)
        )

        // When
        val agrupados = reportes.groupBy { it.tipoAmenaza }

        // Then
        assertEquals(3, agrupados.size) // Phishing, Malware, Scam
        assertEquals(2, agrupados["Phishing"]?.size)
        assertEquals(1, agrupados["Malware"]?.size)
        assertEquals(1, agrupados["Scam"]?.size)
    }

    @Test
    fun `obtener reportes más recientes`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "seguros", "Ninguno", "2025-11-20T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "seguros", "Ninguno", "2025-11-25T02:00:00Z", "{}", "N/A", 1),
            Report(3, "url3", "seguros", "Ninguno", "2025-11-22T03:00:00Z", "{}", "N/A", 1)
        )

        // When
        val ordenados = reportes.sortedByDescending { it.createdAt }

        // Then
        assertEquals("url2", ordenados[0].url) // Más reciente (2025-11-25)
        assertEquals("url3", ordenados[1].url)
        assertEquals("url1", ordenados[2].url)
    }

    @Test
    fun `filtrar solo reportes peligrosos`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "seguros", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "bloqueadas", "Phishing", "2025-11-22T02:00:00Z", "{}", "Google", 1),
            Report(3, "url3", "seguros", "Ninguno", "2025-11-22T03:00:00Z", "{}", "N/A", 1),
            Report(4, "url4", "bloqueadas", "Malware", "2025-11-22T04:00:00Z", "{}", "N/A", 1)
        )

        // When
        val peligrosos = reportes.filter { it.peligro == "bloqueadas" }

        // Then
        assertEquals(2, peligrosos.size)
        assertTrue(peligrosos.all { it.peligro == "bloqueadas" })
        assertTrue(peligrosos.any { it.tipoAmenaza == "Phishing" })
        assertTrue(peligrosos.any { it.tipoAmenaza == "Malware" })
    }

    @Test
    fun `contar URLs que imitan sitios conocidos`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "bloqueadas", "Phishing", "2025-11-22T01:00:00Z", "{}", "Google", 1),
            Report(2, "url2", "bloqueadas", "Phishing", "2025-11-22T02:00:00Z", "{}", "PayPal", 1),
            Report(3, "url3", "bloqueadas", "Phishing", "2025-11-22T03:00:00Z", "{}", "Facebook", 1),
            Report(4, "url4", "bloqueadas", "Malware", "2025-11-22T04:00:00Z", "{}", "N/A", 1)
        )

        // When
        val imitaciones = reportes.count { it.imitaA != "N/A" && it.imitaA?.isNotBlank() == true }

        // Then
        assertEquals(3, imitaciones)
    }

    @Test
    fun `validar que todos los reportes tienen userId`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "seguros", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "bloqueadas", "Phishing", "2025-11-22T02:00:00Z", "{}", "Google", 1)
        )

        // When
        val todosConUserId = reportes.all { it.userId != null && it.userId > 0 }

        // Then
        assertTrue("Todos los reportes deberían tener userId válido", todosConUserId)
    }

    @Test
    fun `reportes vacíos debería retornar estadísticas en cero`() {
        // Given
        val reportes = emptyList<Report>()

        // When
        val seguros = reportes.count { it.peligro == "seguros" }
        val peligrosos = reportes.count { it.peligro == "bloqueadas" }
        val sospechosos = reportes.count { it.peligro == "sospechosos" }

        // Then
        assertEquals(0, seguros)
        assertEquals(0, peligrosos)
        assertEquals(0, sospechosos)
        assertEquals(0, reportes.size)
    }

    @Test
    fun `encontrar amenaza más común`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "bloqueadas", "Phishing", "2025-11-22T01:00:00Z", "{}", "Google", 1),
            Report(2, "url2", "bloqueadas", "Phishing", "2025-11-22T02:00:00Z", "{}", "PayPal", 1),
            Report(3, "url3", "bloqueadas", "Phishing", "2025-11-22T03:00:00Z", "{}", "Facebook", 1),
            Report(4, "url4", "bloqueadas", "Malware", "2025-11-22T04:00:00Z", "{}", "N/A", 1)
        )

        // When
        val amenazaMasComun = reportes
            .groupBy { it.tipoAmenaza }
            .maxByOrNull { it.value.size }
            ?.key

        // Then
        assertEquals("Phishing", amenazaMasComun)
    }

    @Test
    fun `validar formato de timestamp`() {
        // Given
        val reporte = Report(
            1, "url1", "seguros", "Ninguno", 
            "2025-11-22T01:00:00Z", "{}", "N/A", 1
        )

        // When
        val timestamp = reporte.createdAt
        val tieneFormatoISO = timestamp?.contains("T") == true && timestamp.contains("Z")

        // Then
        assertTrue("Timestamp debería tener formato ISO 8601", tieneFormatoISO)
    }

    @Test
    fun `comparar dos reportes por fecha`() {
        // Given
        val reporte1 = Report(1, "url1", "seguros", "Ninguno", "2025-11-20T01:00:00Z", "{}", "N/A", 1)
        val reporte2 = Report(2, "url2", "seguros", "Ninguno", "2025-11-25T01:00:00Z", "{}", "N/A", 1)

        // When
        val reporte2EsMasReciente = (reporte2.createdAt ?: "") > (reporte1.createdAt ?: "")

        // Then
        assertTrue("Reporte 2 debería ser más reciente", reporte2EsMasReciente)
    }
}
