package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.LoginUiState
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.text.toInt
import android.util.Base64
import android.util.Log
import org.json.JSONObject


class LoginViewModel(
    application: Application,
    private val authRepository: AuthRepository = AuthRepository(),
    private val sessionManager: SessionManager = SessionManager(application)
) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, error = null) }
    }

    fun onContrasenaChange(valor: String) {
        _estado.update { it.copy(contrasena = valor, error = null) }
    }

    private fun validarCampos(): String? {
        val estadoActual = _estado.value

        return when {
            estadoActual.correoElectronico.isBlank() ->
                "El correo electrónico no puede estar vacío"

            !estadoActual.correoElectronico.contains("@") ->
                "Ingresa un correo electrónico válido"

            estadoActual.contrasena.isBlank() ->
                "La contraseña no puede estar vacía"

            else -> null
        }
    }

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
                        // Extrae información del JWT
                        val (nombre, correo) = extraerInfoDelToken(loginResponse.token)
                        Log.d("LoginViewModel", "Info del token - Nombre: $nombre, Email: $correo")

                        // Guarda la sesión con los datos del token
                        sessionManager.guardarSesionCompleta(
                            idUsuario = 0, // No tenemos ID del usuario
                            nombre = nombre,
                            correo = correo,
                            token = loginResponse.token
                        )

                        Log.d("LoginViewModel", "Sesión guardada exitosamente")
                        _estado.update { it.copy(cargando = false, sesionIniciada = true) }
                        onLoginExitoso()

                    } catch (e: Exception) {
                        Log.e("LoginViewModel", "Error al decodificar token: ${e.message}", e)
                        _estado.update {
                            it.copy(
                                cargando = false,
                                mensajeError = "Error al procesar la respuesta del servidor"
                            )
                        }
                    }
                },
                onFailure = { error ->
                    Log.e("LoginViewModel", "Error en login: ${error.message}", error)
                    _estado.update {
                        it.copy(
                            cargando = false,
                            mensajeError = when {
                                error.message?.contains("401") == true ->
                                    "Credenciales incorrectas"
                                error.message?.contains("404") == true ->
                                    "Usuario no encontrado"
                                error.message?.contains("timeout") == true ->
                                    "Error de conexión con el servidor"
                                else ->
                                    error.message ?: "Error al iniciar sesión"
                            }
                        )
                    }
                }
            )
        }
    }

    // Función auxiliar para decodificar el JWT
    private fun extraerInfoDelToken(token: String): Pair<String, String> {
        val parts = token.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Token JWT inválido")

        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
        val json = JSONObject(payload)

        val nombre = json.optString("name", "Usuario")
        val email = json.optString("sub", "")

        return Pair(nombre, email)
    }

    fun verificarSesionActiva(onSesionActiva: () -> Unit) {
        viewModelScope.launch {
            if (sessionManager.hasActiveSession()) {
                onSesionActiva()
            }
        }
    }

    fun clearError() {
        _estado.update { it.copy(error = null, mensajeError = null) }
    }
}
