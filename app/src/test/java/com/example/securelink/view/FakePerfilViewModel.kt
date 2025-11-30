package com.example.securelink.view

import com.example.securelink.model.Data.Usuario
import com.example.securelink.model.Report
import com.example.securelink.viewmodel.PerfilViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import io.mockk.mockk

/**
 * Un ViewModel falso para usar en pruebas de Compose.
 * Permite controlar directamente los estados emitidos.
 */
class FakePerfilViewModel : PerfilViewModel(context = mockk(relaxed = true)) {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    override val usuario: StateFlow<Usuario?> = _usuario

    private val _historialAnalisis = MutableStateFlow<List<Report>>(emptyList())
    override val historialAnalisis: StateFlow<List<Report>> = _historialAnalisis

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading

    // Sobrescribimos cargarDatos para que no haga nada y no interfiera con la prueba.
    override fun cargarDatos() {
        // No ejecutar la lógica real de carga.
    }

    // Funciones para manipular el estado desde la prueba
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setUsuario(user: Usuario?) {
        _usuario.value = user
    }

    fun setHistorial(historial: List<Report>) {
        _historialAnalisis.value = historial
    }
}
