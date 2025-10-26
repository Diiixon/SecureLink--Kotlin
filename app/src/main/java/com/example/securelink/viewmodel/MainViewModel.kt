package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.AppDatabase
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.launch

// ViewModel principal de la aplicación, responsable de acciones globales como cerrar sesión.
class MainViewModel(application: Application): AndroidViewModel(application) {

    private val authRepository: AuthRepository

    init {
        // Prepara el repositorio de autenticación al iniciar el ViewModel.
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        val sessionManager = SessionManager(application)
        authRepository = AuthRepository(usuarioDao, sessionManager)
    }

    // Llama al repositorio para cerrar la sesión del usuario actual.
    fun cerrarSesion(){
        viewModelScope.launch {
            authRepository.cerrarSesion()
        }
    }
}