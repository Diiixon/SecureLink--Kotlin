package com.example.securelink.viewmodel

import android.app.Application
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.LoginUiState
import com.example.securelink.network.RetrofitClient
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * ViewModel para la pantalla de inicio de sesión.
 *
 * @param application La aplicación.
 * @param authRepository El repositorio para operaciones de datos relacionadas con la autenticación.
 * @param sessionManager El administrador de sesiones.
 */
class LoginViewModel(
    application: Application,
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager = SessionManager(application)
) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    /**
     * Se llama cuando cambia el correo electrónico.
     *
     * @param valor El nuevo correo electrónico.
     */
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, error = null) }
    }

    /**
     * Se llama cuando cambia la contraseña.
     *
     * @param valor La nueva contraseña.
     */
    fun onContrasenaChange(valor: String) {
        _estado.update { it.copy(contrasena = valor, error = null) }
    }

    /**
     * Valida los campos.
     *
     * @return Un mensaje de error, o nulo si los campos son válidos.
     */
    private fun validarCampos(): String? {
        val estadoActual = _estado.value
        return when {
            estadoActual.correoElectronico.isBlank() -> "El correo electrónico no puede estar vacío"
            !estadoActual.correoElectronico.contains("@") -> "Ingresa un correo electrónico válido"
            estadoActual.contrasena.isBlank() -> "La contraseña no puede estar vacía"
            else -> null
        }
    }

    /**
     * Inicia sesión del usuario.
     *
     * @param onLoginExitoso Una devolución de llamada que se llamará cuando el inicio de sesión sea exitoso.
     */
    fun iniciarSesion(onLoginExitoso: () -> Unit = {}) {
        viewModelScope.launch {
            Log.d("LoginViewModel", "=== INICIANDO SESIÓN ===")
            _estado.update { it.copy(cargando = true, mensajeError = null) }

            val mensajeError = validarCampos()
            if (mensajeError != null) {
                Log.e("LoginViewModel", "Validación fallida: $mensajeError")
                _estado.update { it.copy(cargando = false, mensajeError = mensajeError) }
                return@launch
            }

            val email = _estado.value.correoElectronico.trim()
            val password = _estado.value.contrasena
            Log.d("LoginViewModel", "Intentando login con email: $email")

            val resultado = authRepository.login(email = email, password = password)

            resultado.fold(
                onSuccess = { loginResponse ->
                    Log.d("LoginViewModel", "Token recibido: ${loginResponse.token}")
                    try {
                        // CORRECCIÓN: Se extrae también el userId del token
                        val (nombre, correo, userId) = extraerInfoDelToken(loginResponse.token)
                        Log.d("LoginViewModel", "Info del token - Nombre: $nombre, Email: $correo, UserId: $userId")

                        // CORRECCIÓN: Se guarda el userId extraído del token
                        sessionManager.guardarSesionCompleta(
                            idUsuario = userId,
                            nombre = nombre,
                            correo = correo,
                            token = loginResponse.token
                        )

                        Log.d("LoginViewModel", "Sesión guardada exitosamente")
                        _estado.update { it.copy(cargando = false, sesionIniciada = true) }
                        onLoginExitoso()

                    } catch (e: Exception) {
                        Log.e("LoginViewModel", "Error al decodificar token: ${e.message}", e)
                        _estado.update { it.copy(cargando = false, mensajeError = "Error al procesar la respuesta del servidor") }
                    }
                },
                onFailure = { error ->
                    Log.e("LoginViewModel", "Error en login: ${error.message}", error)
                    _estado.update {
                        it.copy(
                            cargando = false,
                            mensajeError = when {
                                error.message?.contains("401") == true -> "Credenciales incorrectas"
                                error.message?.contains("404") == true -> "Usuario no encontrado"
                                error.message?.contains("timeout") == true -> "Error de conexión con el servidor"
                                else -> error.message ?: "Error al iniciar sesión"
                            }
                        )
                    }
                }
            )
        }
    }

    /**
     * Extrae información del token.
     * CORRECCIÓN: La función ahora devuelve un Triple con el userId.
     *
     * @param token El token.
     * @return Un Triple que contiene el nombre, el correo electrónico y el userId.
     */
    private fun extraerInfoDelToken(token: String): Triple<String, String, Int> {
        val parts = token.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Token JWT inválido")

        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
        val json = JSONObject(payload)

        val nombre = json.optString("name", "Usuario")
        val email = json.optString("sub", "")
        // CORRECCIÓN: Se extrae el 'userId' del token, con un valor por defecto de 0
        val userId = json.optInt("userId", 0)

        return Triple(nombre, email, userId)
    }

    /**
     * Comprueba si hay una sesión activa.
     *
     * @param onSesionActiva Una devolución de llamada que se llamará si hay una sesión activa.
     */
    fun verificarSesionActiva(onSesionActiva: () -> Unit) {
        viewModelScope.launch {
            if (sessionManager.hasActiveSession()) {
                onSesionActiva()
            }
        }
    }

    /**
     * Borra el mensaje de error.
     */
    fun clearError() {
        _estado.update { it.copy(error = null, mensajeError = null) }
    }

    companion object {
        /**
         * Proporciona una fábrica para crear instancias de [LoginViewModel].
         *
         * @param application La aplicación.
         * @return Una fábrica para crear instancias de [LoginViewModel].
         */
        fun provideFactory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val authService = RetrofitClient.authApiService
                    val authRepository = AuthRepository(authService)
                    return LoginViewModel(
                        application = application,
                        authRepository = authRepository,
                        sessionManager = SessionManager(application)
                    ) as T
                }
            }
    }
}