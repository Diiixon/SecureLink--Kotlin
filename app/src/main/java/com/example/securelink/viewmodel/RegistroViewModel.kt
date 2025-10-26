package com.example.securelink.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.AppDatabase
import com.example.securelink.repository.AuthRepository
import com.example.securelink.model.RegistroUiState
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado.asStateFlow()

    private val authRepository: AuthRepository

    init {
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        val sessionManager = SessionManager(application)
        authRepository = AuthRepository(usuarioDao, sessionManager)
    }

    fun onNombreUsuarioChange(valor: String) {
        _estado.update { it.copy(nombreUsuario = valor, errores = it.errores.copy(nombreUsuario = null)) }
    }
    fun onCorreoElectronicoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, errores = it.errores.copy(correoElectronico = null)) }
    }
    fun onContrasenaChange(valor: String) {
        _estado.update { it.copy(contrasena = valor, errores = it.errores.copy(contrasena = null)) }
    }
    fun onContrasenaConfirmadaChange(valor: String) {
        _estado.update { it.copy(contrasenaConfirmada = valor, errores = it.errores.copy(contrasenaConfirmada = null)) }
    }

    private fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombreUsuario = if (estadoActual.nombreUsuario.isBlank()) "Campo obligatorio" else null,
            correoElectronico = if (!estadoActual.correoElectronico.contains("@")) "Correo Inválido" else null,
            contrasena = if (estadoActual.contrasena.length < 8) "Debe tener al menos 8 caracteres" else null,
            contrasenaConfirmada = if (estadoActual.contrasenaConfirmada != estadoActual.contrasena) "Las contraseñas no coinciden" else null
        )
        val hayErrores = listOfNotNull(errores.nombreUsuario, errores.correoElectronico, errores.contrasena, errores.contrasenaConfirmada).isNotEmpty()
        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    fun registrarUsuario(onRegistroExitoso: () -> Unit) {
        if (!validarFormulario()) {
            Log.w("RegistroViewModel", "Validación falló.")
            return
        }

        viewModelScope.launch {
            try {
                val estadoActual = _estado.value
                val usuarioGuardado = authRepository.registrarUsuario(
                    nombre = estadoActual.nombreUsuario,
                    correo = estadoActual.correoElectronico,
                    contrasena = estadoActual.contrasena
                )

                if (usuarioGuardado != null) {
                    _estado.update { it.copy(registroExitoso = true) }
                    onRegistroExitoso()
                } else {
                    // Correo ya existe
                    _estado.update {
                        it.copy(errores = it.errores.copy(correoElectronico = "El correo ya está en uso"))
                    }
                }
            } catch (e: Exception) {
                Log.e("RegistroViewModel", "Error inesperado: ${e.message}")
                _estado.update {
                    it.copy(errores = it.errores.copy(correoElectronico = "Error inesperado en la BD"))
                }
            }
        }
    }

    fun resetRegistroExitoso() {
        _estado.update { it.copy(registroExitoso = false) }
    }
}