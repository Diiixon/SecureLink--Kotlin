package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.RecuperarUiState
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecuperarViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(RecuperarUiState())
    val estado: StateFlow<RecuperarUiState> = _estado.asStateFlow()

    private val authRepository = AuthRepository()
    private val sessionManager = SessionManager(application)

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, error = null) }
    }

    fun enviarCorreoRecuperacion() {
        val correo = _estado.value.correoElectronico

        // Validación del correo
        if (correo.isBlank()) {
            _estado.update { it.copy(error = "El correo no puede estar vacío") }
            return
        }

        if (!correo.contains("@")) {
            _estado.update { it.copy(error = "Ingresa un correo válido") }
            return
        }

        viewModelScope.launch {
            _estado.update { it.copy(isLoading = true, error = null) }

            // ⭐ Llamar al endpoint de recuperación de contraseña (si existe en tu API)
            // authRepository.recuperarContrasena(correo).fold(
            //     onSuccess = {
            //         _estado.update { it.copy(enlaceEnviado = true, isLoading = false) }
            //     },
            //     onFailure = { error ->
            //         _estado.update {
            //             it.copy(
            //                 error = error.message ?: "Error al enviar el correo",
            //                 isLoading = false
            //             )
            //         }
            //     }
            // )

            // ⭐ MIENTRAS NO TENGAS EL ENDPOINT (simulación):
            kotlinx.coroutines.delay(1500) // Simula delay de red
            _estado.update { it.copy(enlaceEnviado = true, isLoading = false) }
        }
    }

    fun clearError() {
        _estado.update { it.copy(error = null) }
    }

    fun resetState() {
        _estado.update { it.copy(enlaceEnviado = false) }
    }
}
