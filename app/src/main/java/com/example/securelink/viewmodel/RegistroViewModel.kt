package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.RegistroUiState
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.text.toInt

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado.asStateFlow()

    private val authRepository = AuthRepository()
    private val sessionManager = SessionManager(application)

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, error = null) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, error = null) }
    }

    fun onContrasenaChange(valor: String) {
        _estado.update { it.copy(contrasena = valor, error = null) }
    }

    fun onConfirmarContrasenaChange(valor: String) {
        _estado.update { it.copy(confirmarContrasena = valor, error = null) }
    }

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
                // Guardar datos de sesión con el nombre correcto de la propiedad
                sessionManager.guardarSesionCompleta(
                    idUsuario = response.usuario.idUsuario.toInt(),
                    nombre = response.usuario.nombre,
                    correo = response.usuario.correoElectronico,  // ⭐ CAMBIO: correoElectronico en lugar de email
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


    fun clearError() {
        _estado.update { it.copy(error = null) }
    }
}
