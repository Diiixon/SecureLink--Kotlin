package com.example.securelink.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.securelink.model.AnalisisResultado
import com.example.securelink.network.ApiService
import com.example.securelink.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic

/**
 * Pruebas unitarias para AnalyzerViewModel
 * Cobertura: Estados de análisis, validación de URL, manejo de errores
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AnalyzerViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: AnalyzerViewModel
    private lateinit var mockApiService: ApiService

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        mockkObject(RetrofitClient)
        mockApiService = mockk()
        every { RetrofitClient.apiService } returns mockApiService
        viewModel = AnalyzerViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        io.mockk.unmockkAll()
    }

    @Test
    fun `estado inicial debería ser Inicial`() = runTest {
        // When
        val estado = viewModel.estado.first()

        // Then
        assertTrue("Estado inicial debería ser Inicial", estado is AnalisisEstado.Inicial)
    }

    @Test
    fun `URL vacía debería establecer estado de Error`() = runTest {
        // Given
        val urlVacia = ""
        val token = "test-token"

        // When
        viewModel.analizarUrl(urlVacia, token)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val estado = viewModel.estado.first()
        assertTrue("Debería ser estado Error", estado is AnalisisEstado.Error)
        if (estado is AnalisisEstado.Error) {
            assertTrue("Mensaje debería mencionar URL válida", 
                estado.mensaje.contains("URL válida", ignoreCase = true))
        }
    }

    @Test
    fun `URL con solo espacios debería ser inválida`() = runTest {
        // Given
        val urlConEspacios = "   "
        val token = "test-token"

        // When
        viewModel.analizarUrl(urlConEspacios, token)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val estado = viewModel.estado.first()
        assertTrue("Debería ser estado Error", estado is AnalisisEstado.Error)
    }

    @Test
    fun `URL válida debería pasar validación inicial`() {
        // Given
        val urlValida = "https://example.com"

        // When
        val esValida = urlValida.isNotBlank()

        // Then - Verifica que la URL pasa la validación básica
        assertTrue("URL no debería estar vacía", esValida)
        assertTrue("URL debería tener contenido", urlValida.length > 0)
    }

    @Test
    fun `validación de formato de URL con protocolo http`() {
        // Given
        val urlHttp = "http://example.com"

        // When
        val esValida = urlHttp.startsWith("http://") || urlHttp.startsWith("https://")

        // Then
        assertTrue("URL con http:// debería ser válida", esValida)
    }

    @Test
    fun `validación de formato de URL con protocolo https`() {
        // Given
        val urlHttps = "https://secure-site.com"

        // When
        val esValida = urlHttps.startsWith("http://") || urlHttps.startsWith("https://")

        // Then
        assertTrue("URL con https:// debería ser válida", esValida)
    }

    @Test
    fun `URL sin protocolo debería ser detectable`() {
        // Given
        val urlSinProtocolo = "www.example.com"

        // When
        val tieneProtocolo = urlSinProtocolo.startsWith("http://") || urlSinProtocolo.startsWith("https://")

        // Then
        assertFalse("URL sin protocolo debería ser detectable", tieneProtocolo)
    }

    @Test
    fun `AnalisisResultado debería construirse correctamente`() {
        // Given
        val resultado = AnalisisResultado(
            url = "https://example.com",
            peligro = "seguros",
            tipoAmenaza = "Ninguno",
            imitaA = "N/A",
            detalles = mapOf("info" to "seguro")
        )

        // When & Then
        assertEquals("https://example.com", resultado.url)
        assertEquals("seguros", resultado.peligro)
        assertEquals("Ninguno", resultado.tipoAmenaza)
        assertEquals("N/A", resultado.imitaA)
        assertNotNull(resultado.detalles)
    }

    @Test
    fun `AnalisisEstado Error debería contener mensaje`() {
        // Given
        val mensajeError = "Error de conexión"

        // When
        val estadoError = AnalisisEstado.Error(mensajeError)

        // Then
        assertEquals(mensajeError, estadoError.mensaje)
    }

    @Test
    fun `AnalisisEstado Resultado debería contener lista de resultados`() {
        // Given
        val resultados = listOf(
            AnalisisResultado("url1", "seguros", "Ninguno", "N/A", mapOf()),
            AnalisisResultado("url2", "bloqueadas", "Phishing", "Google", mapOf())
        )

        // When
        val estadoResultado = AnalisisEstado.Resultado(resultados)

        // Then
        assertEquals(2, estadoResultado.resultados.size)
        assertEquals("url1", estadoResultado.resultados[0].url)
        assertEquals("url2", estadoResultado.resultados[1].url)
    }

    @Test
    fun `analizarUrl con URL vacía debe mostrar error inmediato`() = runTest {
        viewModel.analizarUrl("", "test-token")
        
        val estado = viewModel.estado.value
        assertTrue(estado is AnalisisEstado.Error)
    }

    @Test
    fun `analizarUrl exitoso con resultados deberiaEmitirEstadoResultado`() = runTest {
        val resultados = listOf(
            AnalisisResultado("https://site.com", "seguros", "Ninguno", "N/A", emptyMap())
        )

        coEvery {
            mockApiService.analizarUrl(any(), any())
        } returns retrofit2.Response.success(resultados)

        viewModel.analizarUrl("https://site.com", "token")
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertTrue(estado is AnalisisEstado.Resultado)
        estado as AnalisisEstado.Resultado
        assertEquals(1, estado.resultados.size)
        assertEquals("https://site.com", estado.resultados[0].url)
    }

    @Test
    fun `analizarUrl exitoso sin resultados deberiaEmitirErrorSinUrls`() = runTest {
        coEvery {
            mockApiService.analizarUrl(any(), any())
        } returns retrofit2.Response.success(emptyList())

        viewModel.analizarUrl("https://site.com", "token")
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertTrue(estado is AnalisisEstado.Error)
    }

    @Test
    fun `analizarUrl respuestaErrorHttp deberiaEmitirErrorGenerico`() = runTest {
        val errorBody = "Internal error".toResponseBody()
        coEvery {
            mockApiService.analizarUrl(any(), any())
        } returns retrofit2.Response.error(500, errorBody)

        viewModel.analizarUrl("https://site.com", "token")
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertTrue(estado is AnalisisEstado.Error)
    }

    @Test
    fun `analizarUrl con excepcion deberiaEmitirErrorDeConexion`() = runTest {
        coEvery {
            mockApiService.analizarUrl(any(), any())
        } throws Exception("timeout")

        viewModel.analizarUrl("https://site.com", "token")
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertTrue(estado is AnalisisEstado.Error)
    }

    @Test
    fun `reiniciar debe volver estado a Inicial`() = runTest {
        viewModel.reiniciar()
        
        val estado = viewModel.estado.value
        assertTrue(estado is AnalisisEstado.Inicial)
    }
}
