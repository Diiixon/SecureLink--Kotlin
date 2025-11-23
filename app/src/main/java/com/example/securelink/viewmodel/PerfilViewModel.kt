package com.example.securelink.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.Usuario
import com.example.securelink.model.Report
import com.example.securelink.repository.ReportesRepository
import com.example.securelink.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(context: Context) : ViewModel() {
    private val usuarioRepository = UsuarioRepository(context)
    private val reportesRepository = ReportesRepository(context)

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _historialAnalisis = MutableStateFlow<List<Report>>(emptyList())
    val historialAnalisis: StateFlow<List<Report>> = _historialAnalisis

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun cargarDatos() {
        if (_isLoading.value) return // Evita múltiples llamadas simultáneas

        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("PerfilViewModel", "Cargando datos del usuario...")
                _usuario.value = usuarioRepository.obtenerUsuarioActual()
                Log.d("PerfilViewModel", "Usuario cargado: ${_usuario.value?.nombre}")

                val result = reportesRepository.obtenerReportesUsuario()
                result.onSuccess { reportes ->
                    _historialAnalisis.value = reportes
                    Log.d("PerfilViewModel", "Historial cargado: ${reportes.size} reportes")
                    
                    // Log detallado de cada reporte para debugging
                    reportes.forEachIndexed { index, reporte ->
                        Log.d("PerfilViewModel", "Reporte $index: url=${reporte.url}")
                        Log.d("PerfilViewModel", "  peligro='${reporte.peligro}' (length=${reporte.peligro?.length})")
                        Log.d("PerfilViewModel", "  tipoAmenaza='${reporte.tipoAmenaza}' (length=${reporte.tipoAmenaza?.length})")
                        Log.d("PerfilViewModel", "  imitaA='${reporte.imitaA}' (length=${reporte.imitaA?.length})")
                        Log.d("PerfilViewModel", "  imitaA bytes: ${reporte.imitaA?.toByteArray()?.joinToString()}")
                    }
                }.onFailure { e ->
                    Log.e("PerfilViewModel", "Error al cargar historial: ${e.message}", e)
                }
            } catch (e: Exception) {
                Log.e("PerfilViewModel", "Error al cargar datos: ${e.message}", e)
            } finally {
                _isLoading.value = false
                Log.d("PerfilViewModel", "Carga completada. isLoading = false")
            }
        }
    }
}
