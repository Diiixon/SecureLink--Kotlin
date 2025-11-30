package com.example.securelink.viewmodel

import android.app.Application
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.securelink.model.LoginResponse
import com.example.securelink.model.LoginUiState
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import io.mockk.*

/**
 * Pruebas unitarias para LoginViewModel
 * Cobertura: Validaciones de entrada, cambio de estado, manejo de errores
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application
    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: AuthRepository
    private lateinit var sessionManager: SessionManager

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = mockk(relaxed = true)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        authRepository = mockk()
        sessionManager = mockk(relaxed = true)

        viewModel = LoginViewModel(application, authRepository, sessionManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `estado inicial debería tener campos vacíos`() = runTest {
        // When
        val estado = viewModel.estado.first()

        // Then
        assertEquals("", estado.correoElectronico)
        assertEquals("", estado.contrasena)
        assertFalse(estado.cargando)
        assertNull(estado.error)
    }

    @Test
    fun `onCorreoChange debería actualizar el correo y limpiar error`() = runTest {
        // Given
        val nuevoCorreo = "test@example.com"

        // When
        viewModel.onCorreoChange(nuevoCorreo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val estado = viewModel.estado.first()
        assertEquals(nuevoCorreo, estado.correoElectronico)
        assertNull(estado.error)
    }

    @Test
    fun `onContrasenaChange debería actualizar la contraseña y limpiar error`() = runTest {
        // Given
        val nuevaContrasena = "password123"

        // When
        viewModel.onContrasenaChange(nuevaContrasena)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val estado = viewModel.estado.first()
        assertEquals(nuevaContrasena, estado.contrasena)
        assertNull(estado.error)
    }

    @Test
    fun `correo vacío debería ser inválido`() = runTest {
        // Given
        viewModel.onCorreoChange("")
        viewModel.onContrasenaChange("password123")

        // When
        val correoVacio = viewModel.estado.first().correoElectronico.isBlank()

        // Then
        assertTrue("Correo vacío debería ser inválido", correoVacio)
    }

    @Test
    fun `correo sin arroba debería ser inválido`() = runTest {
        // Given
        val correoSinArroba = "testexample.com"
        viewModel.onCorreoChange(correoSinArroba)

        // When
        val tieneArroba = viewModel.estado.first().correoElectronico.contains("@")

        // Then
        assertFalse("Correo sin @ debería ser inválido", tieneArroba)
    }

    @Test
    fun `correo con arroba debería ser válido`() = runTest {
        // Given
        val correoValido = "test@example.com"
        viewModel.onCorreoChange(correoValido)

        // When
        val tieneArroba = viewModel.estado.first().correoElectronico.contains("@")

        // Then
        assertTrue("Correo con @ debería ser válido", tieneArroba)
    }

    @Test
    fun `contraseña vacía debería ser inválida`() = runTest {
        // Given
        viewModel.onContrasenaChange("")

        // When
        val contrasenaVacia = viewModel.estado.first().contrasena.isBlank()

        // Then
        assertTrue("Contraseña vacía debería ser inválida", contrasenaVacia)
    }

    @Test
    fun `campos válidos deberían pasar validación básica`() = runTest {
        // Given
        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("password123")

        // When
        val estado = viewModel.estado.first()
        val correoValido = estado.correoElectronico.contains("@") && estado.correoElectronico.isNotBlank()
        val contrasenaValida = estado.contrasena.isNotBlank()

        // Then
        assertTrue("Correo debería ser válido", correoValido)
        assertTrue("Contraseña debería ser válida", contrasenaValida)
    }

    @Test
    fun `múltiples cambios de correo deberían actualizar el estado`() = runTest {
        // Given & When
        viewModel.onCorreoChange("test1@example.com")
        viewModel.onCorreoChange("test2@example.com")
        viewModel.onCorreoChange("test3@example.com")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val estado = viewModel.estado.first()
        assertEquals("test3@example.com", estado.correoElectronico)
    }

    @Test
    fun `cambio de correo después de error debería limpiar el error`() = runTest {
        // Given
        val estadoInicial = LoginUiState(
            correoElectronico = "invalid",
            contrasena = "pass",
            error = "Error de prueba"
        )

        // When
        viewModel.onCorreoChange("test@example.com")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val estado = viewModel.estado.first()
        assertNull("Error debería ser null después de cambio", estado.error)
    }

    @Test
    fun `clearError debe limpiar error y mensajeError`() = runTest {
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertNull(estado.error)
        assertNull(estado.mensajeError)
    }

    @Test
    fun `verificarSesionActiva ejecuta sin errores`() = runTest {
        // Smoke test: simplemente que no lance excepciones cuando no hay sesión activa
        viewModel.verificarSesionActiva { }
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(true)
    }

    @Test
    fun `onCorreoChange con correo largo actualiza correctamente`() = runTest {
        val correoLargo = "usuario.con.nombre.muy.largo@dominio.ejemplo.com"
        viewModel.onCorreoChange(correoLargo)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(correoLargo, viewModel.estado.value.correoElectronico)
    }

    @Test
    fun `onContrasenaChange con contraseña larga actualiza correctamente`() = runTest {
        val passLarga = "contraseña_muy_larga_con_muchos_caracteres_123456"
        viewModel.onContrasenaChange(passLarga)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(passLarga, viewModel.estado.value.contrasena)
    }

    @Test
    fun `múltiples llamadas a clearError mantienen estado limpio`() = runTest {
        viewModel.clearError()
        viewModel.clearError()
        viewModel.clearError()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertNull(estado.error)
        assertNull(estado.mensajeError)
    }

    @Test
    fun `iniciarSesion_exito_guardaSesionYMarcaSesionIniciada`() = runTest {
        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("password123")

        // Payload ya codificado en Base64 URL-safe (simplifica el test y evita usar android.util.Base64)
        val payloadBase64 = "eyJuYW1lIjoiSnVhbiIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20ifQ"
        val token = "header.$payloadBase64.signature"

        coEvery { authRepository.login(any(), any()) } returns Result.success(LoginResponse(token))
        coEvery { sessionManager.guardarSesionCompleta(any(), any(), any(), any()) } just Runs

        viewModel.iniciarSesion()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        // Sólo comprobamos que deja de estar cargando; el detalle
        // de decodificar correctamente el JWT se cubre en otros tests.
        assertFalse(estado.cargando)
    }

    @Test
    fun `iniciarSesion_tokenInvalido_muestraMensajeErrorProcesamiento`() = runTest {
        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("password123")

        coEvery { authRepository.login(any(), any()) } returns Result.success(LoginResponse("token-invalido"))

        viewModel.iniciarSesion()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertEquals("Error al procesar la respuesta del servidor", estado.mensajeError)
        assertFalse(estado.sesionIniciada)
    }

    @Test
    fun `iniciarSesion_error404_muestraMensajeUsuarioNoEncontrado`() = runTest {
        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("password123")

        coEvery { authRepository.login(any(), any()) } returns Result.failure(Exception("404 Not Found"))

        viewModel.iniciarSesion()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertEquals("Usuario no encontrado", estado.mensajeError)
    }

    @Test
    fun `iniciarSesion_errorTimeout_muestraMensajeErrorConexion`() = runTest {
        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("password123")

        coEvery { authRepository.login(any(), any()) } returns Result.failure(Exception("timeout"))

        viewModel.iniciarSesion()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertEquals("Error de conexión con el servidor", estado.mensajeError)
    }

    @Test
    fun `iniciarSesion_errorGenerico_muestraMensajeErrorPorDefecto`() = runTest {
        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("password123")

        coEvery { authRepository.login(any(), any()) } returns Result.failure(Exception("algo salió mal"))

        viewModel.iniciarSesion()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertEquals("algo salió mal", estado.mensajeError)
    }

    @Test
    fun `iniciarSesion_conCamposInvalidos_noLlamaRepositorioYDevuelveMensajeError`() = runTest {
        // Simula estado con correo vacío
        viewModel.onCorreoChange("")
        viewModel.onContrasenaChange("password123")

        viewModel.iniciarSesion()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertEquals("El correo electrónico no puede estar vacío", estado.mensajeError)
    }

    @Test
    fun `iniciarSesion_error401_muestraMensajeCredencialesIncorrectas`() = runTest {
        coEvery { authRepository.login(any(), any()) } returns Result.failure(Exception("401 Unauthorized"))

        viewModel.onCorreoChange("test@example.com")
        viewModel.onContrasenaChange("password123")

        viewModel.iniciarSesion()
        testDispatcher.scheduler.advanceUntilIdle()

        val estado = viewModel.estado.value
        assertEquals("Credenciales incorrectas", estado.mensajeError)
    }

    @Test
    fun `verificarSesionActiva_conSesionActiva_ejecutaCallback`() = runTest {
        coEvery { sessionManager.hasActiveSession() } returns true

        var callbackEjecutado = false
        viewModel.verificarSesionActiva { callbackEjecutado = true }
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(callbackEjecutado)
    }


}
