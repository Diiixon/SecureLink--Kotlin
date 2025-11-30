package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.SessionManager
import kotlinx.coroutines.launch

/**
 * ViewModel principal de la aplicación, responsable de acciones globales como cerrar sesión.
 *
 * @param application La aplicación.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    /**
     * Cierra la sesión del usuario actual limpiando el DataStore.
     */
    fun cerrarSesion() {
        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }

    /**
     * Verifica si hay una sesión activa.
     *
     * @return Verdadero si hay una sesión activa, falso en caso contrario.
     */
    suspend fun hasActiveSession(): Boolean {
        return sessionManager.hasActiveSession()
    }

    /**
     * Obtiene el nombre del usuario (opcional, para mostrarlo en la UI).
     *
     * @return El nombre del usuario, o nulo si no está disponible.
     */
    suspend fun getUserName(): String? {
        return sessionManager.getUserName()
    }
}