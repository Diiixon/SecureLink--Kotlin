package com.example.securelink.repository

import android.content.Context
import com.example.securelink.model.Report
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class ReportesRepositoryTest {

    private lateinit var repository: ReportesRepository
    private lateinit var context: Context

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        repository = ReportesRepository(context)
    }

    // ========== Tests para calcularEstadisticas() ==========

    @Test
    fun `calcularEstadisticas con reportes seguros debe contarlos correctamente`() {
        val reportes = listOf(
            Report(1, "url1", "seguro", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "SEGURO", "Ninguno", "2025-11-22T02:00:00Z", "{}", "N/A", 1)
        )

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(2, resultado.totalReportes)
        assertEquals(2, resultado.urlsSeguras)
        assertEquals(0, resultado.urlsSospechosas)
        assertEquals(0, resultado.urlsPeligrosas)
    }

    @Test
    fun `calcularEstadisticas con reportes sospechosos debe contarlos correctamente`() {
        val reportes = listOf(
            Report(1, "url1", "sospechoso", "Scam", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "SOSPECHOSO", "Phishing", "2025-11-22T02:00:00Z", "{}", "N/A", 1)
        )

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(2, resultado.totalReportes)
        assertEquals(0, resultado.urlsSeguras)
        assertEquals(2, resultado.urlsSospechosas)
        assertEquals(0, resultado.urlsPeligrosas)
    }

    @Test
    fun `calcularEstadisticas con reportes peligrosos debe contarlos correctamente`() {
        val reportes = listOf(
            Report(1, "url1", "peligroso", "Malware", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "PELIGROSO", "Phishing", "2025-11-22T02:00:00Z", "{}", "N/A", 1)
        )

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(2, resultado.totalReportes)
        assertEquals(0, resultado.urlsSeguras)
        assertEquals(0, resultado.urlsSospechosas)
        assertEquals(2, resultado.urlsPeligrosas)
    }

    @Test
    fun `calcularEstadisticas con reportes mixtos debe contarlos todos`() {
        val reportes = listOf(
            Report(1, "url1", "seguro", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "sospechoso", "Scam", "2025-11-22T02:00:00Z", "{}", "N/A", 1),
            Report(3, "url3", "peligroso", "Malware", "2025-11-22T03:00:00Z", "{}", "N/A", 1),
            Report(4, "url4", "seguro", "Ninguno", "2025-11-22T04:00:00Z", "{}", "N/A", 1)
        )

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(4, resultado.totalReportes)
        assertEquals(2, resultado.urlsSeguras)
        assertEquals(1, resultado.urlsSospechosas)
        assertEquals(1, resultado.urlsPeligrosas)
    }

    @Test
    fun `calcularEstadisticas con lista vacía debe retornar ceros`() {
        val reportes = emptyList<Report>()

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(0, resultado.totalReportes)
        assertEquals(0, resultado.urlsSeguras)
        assertEquals(0, resultado.urlsSospechosas)
        assertEquals(0, resultado.urlsPeligrosas)
    }

    @Test
    fun `calcularEstadisticas con peligro null debe contar como sospechoso`() {
        val reportes = listOf(
            Report(1, "url1", null, "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1)
        )

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(1, resultado.totalReportes)
        assertEquals(0, resultado.urlsSeguras)
        assertEquals(1, resultado.urlsSospechosas)
        assertEquals(0, resultado.urlsPeligrosas)
    }

    @Test
    fun `calcularEstadisticas con categoría desconocida debe contar como sospechoso`() {
        val reportes = listOf(
            Report(1, "url1", "desconocido", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "", "Ninguno", "2025-11-22T02:00:00Z", "{}", "N/A", 1)
        )

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(2, resultado.totalReportes)
        assertEquals(0, resultado.urlsSeguras)
        assertEquals(2, resultado.urlsSospechosas)
        assertEquals(0, resultado.urlsPeligrosas)
    }

    @Test
    fun `calcularEstadisticas debe ser case insensitive`() {
        val reportes = listOf(
            Report(1, "url1", "SEGURO", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "Sospechoso", "Scam", "2025-11-22T02:00:00Z", "{}", "N/A", 1),
            Report(3, "url3", "PeLiGrOsO", "Malware", "2025-11-22T03:00:00Z", "{}", "N/A", 1)
        )

        val resultado = repository.calcularEstadisticas(reportes)

        assertEquals(3, resultado.totalReportes)
        assertEquals(1, resultado.urlsSeguras)
        assertEquals(1, resultado.urlsSospechosas)
        assertEquals(1, resultado.urlsPeligrosas)
    }

    @Test
    fun `filtrar por categoría específica debería funcionar`() {
        // Given
        val reportes = listOf(
            Report(1, "url1", "seguros", "Ninguno", "2025-11-22T01:00:00Z", "{}", "N/A", 1),
            Report(2, "url2", "bloqueadas", "Phishing", "2025-11-22T02:00:00Z", "{}", "Google", 1),
            Report(3, "url3", "seguros", "Ninguno", "2025-11-22T03:00:00Z", "{}", "N/A", 1)
        )
        
        // When
        val reportesSeguras = reportes.filter { it.peligro == "seguros" }
        
        // Then
        assertEquals(2, reportesSeguras.size)
        assertTrue(reportesSeguras.all { it.peligro == "seguros" })
    }
}
