package com.example.securelink.viewmodel

import androidx.lifecycle.ViewModel
import com.example.securelink.model.BarData // Corregido: El modelo está en el paquete 'model'.
import com.example.securelink.model.DonutSlice // Corregido: El modelo está en el paquete 'model'.
import com.example.securelink.ui.theme.Gold
import com.example.securelink.ui.theme.Red
import com.example.securelink.ui.theme.Teal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Modelo de datos que representa el estado de la pantalla de Estadísticas.
data class EstadisticasUiState(
    val amenazasUsuario: List<DonutSlice> = emptyList(), // Datos para el gráfico de dona.
    val comparativaData: List<BarData> = emptyList()      // Datos para el gráfico de barras.
)

// ViewModel para la pantalla de Estadísticas.
class EstadisticasViewModel : ViewModel() {

    // StateFlow privado para gestionar el estado internamente.
    private val _uiState = MutableStateFlow(EstadisticasUiState())
    // StateFlow público y de solo lectura que la UI observará.
    val uiState: StateFlow<EstadisticasUiState> = _uiState

    init {
        // Carga los datos de los gráficos al iniciar el ViewModel.
        cargarEstadisticasSimuladas()
    }

    // Prepara y carga datos simulados para los gráficos.
    // En una app real, estos datos vendrían de una base de datos o una API.
    private fun cargarEstadisticasSimuladas() {
        // --- Datos para el Gráfico de Donut ---
        val amenazasData = listOf(
            DonutSlice(label = "Seguros", value = 4f, color = Teal),
            DonutSlice(label = "Sospechosos", value = 11f, color = Gold),
            DonutSlice(label = "Bloqueadas", value = 5f, color = Red)
        )

        // --- Datos para el Gráfico de Barras ---
        val comparativaData = listOf(
            BarData(label = "Seguros", userValue = 2f, globalValue = 5f),
            BarData(label = "Sosp.", userValue = 2.5f, globalValue = 11f),
            BarData(label = "Bloq.", userValue = 4f, globalValue = 1f)
        )

        // Actualiza el estado de la UI con los nuevos datos simulados.
        _uiState.value = EstadisticasUiState(
            amenazasUsuario = amenazasData,
            comparativaData = comparativaData
        )
    }
}