package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.AppDatabase
import com.example.securelink.repository.AuthRepository
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.RecuperarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecuperarViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(RecuperarUiState())
    val estado: StateFlow<RecuperarUiState> = _estado.asStateFlow()

    private val authRepository: AuthRepository

    init {
        // Aunque no lo usemos mucho aquí, es bueno tenerlo listo
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        val sessionManager = SessionManager(application)
        authRepository = AuthRepository(usuarioDao, sessionManager)
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, error = null) }
    }

    fun solicitarRecuperacion(onEnlaceEnviado: () -> Unit) {
        val correo = _estado.value.correoElectronico
        if (correo.isBlank() || !correo.contains("@")) {
            _estado.update { it.copy(error = "Ingrese un correo válido") }
            return
        }

        // Lógica de recuperación
        viewModelScope.launch {
            // TODO: Aquí deberías usar el authRepository para
            // comprobar si el correo existe ANTES de enviar el enlace.
            // val existe = authRepository.correoExiste(correo)
            // if (existe) { ... }

            _estado.update { it.copy(enlaceEnviado = true) }
            onEnlaceEnviado()
        }
    }

    fun onModalDismissed() {
        _estado.update { it.copy(enlaceEnviado = false, correoElectronico = "", error = null) }
    }
}