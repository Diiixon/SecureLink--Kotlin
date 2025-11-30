package com.example.securelink.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.network.RetrofitClient

/**
 * Fábrica para crear instancias de [EstadisticasViewModel].
 *
 * @param context El contexto de la aplicación.
 */
class EstadisticasViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    /**
     * Crea una nueva instancia de la `Class` dada.
     *
     * @param modelClass una `Class` cuya instancia se solicita
     * @return un ViewModel recién creado
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstadisticasViewModel::class.java)) {
            // Obtiene la instancia única del repositorio desde RetrofitClient
            val statsRepository = RetrofitClient.statsRepository
            val sessionManager = SessionManager(context)
            @Suppress("UNCHECKED_CAST")
            return EstadisticasViewModel(statsRepository, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}