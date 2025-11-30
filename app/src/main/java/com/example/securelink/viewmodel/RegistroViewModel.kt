package com.example.securelink.viewmodel

import android.app.Application
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.RegistroUiState
import com.example.securelink.network.RetrofitClient
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * ViewModel para la pantalla de registro.
 *
 * @param application La aplicación.
 */
class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado.asStateFlow()

    // CORRECCIÓN: Se le pasa la dependencia necesaria al constructor de AuthRepository
    private val authRepository = AuthRepository(RetrofitClient.authApiService)
    private val sessionManager = SessionManager(application)

    /**
     * Se llama cuando cambia el nombre.
     *
     * @param valor El nuevo nombre.
     */
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, error = null) }
    }

    /**
     * Se llama cuando cambia el correo electrónico.
     *
     * @param valor El nuevo correo electrónico.
     */
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, error = null) }
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
     * Se llama cuando cambia la confirmación de la contraseña.
     *
     * @param valor La nueva confirmación de la contraseña.
     */
    fun onConfirmarContrasenaChange(valor: String) {
        _estado.update { it.copy(confirmarContrasena = valor, error = null) }
    }

    /**
     * Valida los campos.
     *
     * @return Un mensaje de error, o nulo si los campos son válidos.
     */
    private fun validarCampos(): String? {
        val estadoActual = _estado.value

        return when {
            estadoActual.nombre.isBlank() ->
                "El nombre no puede estar vacío"

            estadoActual.correo.isBlank() ->
                "El correo no puede estar vacío"

            !estadoActual.correo.contains("@") ->
                "Ingresa un correo válido"

            estadoActual.contrasena.isBlank() ->
                "La contraseña no puede estar vacía"

            estadoActual.contrasena.length < 6 ->
                "La contraseña debe tener al menos 6 caracteres"

            estadoActual.contrasena != estadoActual.confirmarContrasena ->
                "Las contraseñas no coinciden"

            else -> null
        }
    }

    /**
     * Registra al usuario.
     *
     * @param onRegistroExitoso Una devolución de llamada que se llamará cuando el registro sea exitoso.
     */
    fun registrarUsuario(onRegistroExitoso: () -> Unit) {
        viewModelScope.launch {
            val errorValidacion = validarCampos()
            if (errorValidacion != null) {
                _estado.update { it.copy(error = errorValidacion) }
                return@launch
            }

            _estado.update { it.copy(isLoading = true, error = null) }

            val estadoActual = _estado.value

            val result = authRepository.register(
                nombre = estadoActual.nombre,
                email = estadoActual.correo,
                password = estadoActual.contrasena
            )

            result.onSuccess { response ->
                Log.d("RegistroViewModel", "Registro exitoso - Token: ${response.token}, UserId: ${response.userId}, Username: ${response.username}")

                // Extraer el nombre del token JWT si username es null
                val nombre = response.username ?: try {
                    extraerNombreDelToken(response.token)
                } catch (e: Exception) {
                    Log.e("RegistroViewModel", "Error al extraer nombre del token", e)
                    estadoActual.nombre // Usar el nombre que ingresó el usuario
                }

                Log.d("RegistroViewModel", "Nombre a guardar: $nombre")

                // Guardar datos de sesión
                sessionManager.guardarSesionCompleta(
                    idUsuario = if (response.userId > 0) response.userId else 1,
                    nombre = nombre,
                    correo = estadoActual.correo,
                    token = response.token
                )

                _estado.update {
                    it.copy(
                        registroExitoso = true,
                        error = null,
                        isLoading = false
                    )
                }

                onRegistroExitoso()
            }.onFailure { error ->
                val mensajeError = when {
                    error.message?.contains("409") == true ->
                        "El correo ya está registrado"

                    error.message?.contains("timeout") == true ->
                        "Error de conexión"

                    else ->
                        error.message ?: "Error al registrar usuario"
                }

                _estado.update {
                    it.copy(
                        registroExitoso = false,
                        error = mensajeError,
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * Extrae el nombre del token.
     *
     * @param token El token.
     * @return El nombre.
     */
    private fun extraerNombreDelToken(token: String): String {
        return try {
            // El token JWT tiene 3 partes separadas por puntos: header.payload.signature
            val partes = token.split(".")
            if (partes.size >= 2) {
                // Decodificar la parte del payload (segunda parte)
                val payloadJson = String(Base64.decode(partes[1], Base64.URL_SAFE or Base64.NO_WRAP))
                val json = JSONObject(payloadJson)

                // Intentar obtener el nombre del campo "name" o "username"
                json.optString("name", null) ?: json.optString("username", "Usuario")
            } else {
                "Usuario"
            }
        } catch (e: Exception) {
            Log.e("RegistroViewModel", "Error al decodificar token", e)
            "Usuario"
        }
    }


    /**
     * Borra el mensaje de error.
     */
    fun clearError() {
        _estado.update { it.copy(error = null) }
    }
}