package com.example.securelink.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.BarData
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.DonutSlice
import com.example.securelink.repository.StatsRepository
import com.example.securelink.ui.theme.SecureBlue
import com.example.securelink.ui.theme.SecureGold
import com.example.securelink.ui.theme.SecureRed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Representa el estado de la interfaz de usuario de la pantalla de estadísticas.
 *
 * @property amenazasUsuario Los datos de amenazas del usuario para el gráfico de dona.
 * @property comparativaData Los datos de comparación para el gráfico de barras.
 * @property isLoading Si los datos se están cargando.
 * @property error Un mensaje de error, si lo hay.
 */
data class EstadisticasUiState(
    val amenazasUsuario: List<DonutSlice> = emptyList(),
    val comparativaData: List<BarData> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel para la pantalla de estadísticas.
 *
 * @param statsRepository El repositorio para operaciones de datos relacionadas con estadísticas.
 * @param sessionManager El administrador de sesiones.
 */
class EstadisticasViewModel(
    private val statsRepository: StatsRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadisticasUiState())
    val uiState: StateFlow<EstadisticasUiState> = _uiState

    init {
        cargarEstadisticas()
    }

    /**
     * Carga las estadísticas.
     */
    private fun cargarEstadisticas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val userId = sessionManager.getUserId()
            if (userId <= 0) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Usuario no válido")
                return@launch
            }

            try {
                val distribucionResponse = statsRepository.getDistribucionUsuario(userId.toLong())
                val comparativaResponse = statsRepository.getComparativaUsuarioVsGlobal(userId.toLong())

                if (distribucionResponse.isSuccessful && comparativaResponse.isSuccessful) {
                    val distribucionData = distribucionResponse.body() ?: emptyList()
                    val comparativaData = comparativaResponse.body()

                    val amenazasUsuario = distribucionData.map {
                        DonutSlice(
                            label = it.estado.replaceFirstChar { char -> char.uppercase() },
                            value = it.cantidad.toFloat(),
                            color = when (it.estado.lowercase()) {
                                "seguros" -> SecureBlue
                                "sospechosos" -> SecureGold
                                "maliciosos", "bloqueadas" -> SecureRed
                                else -> SecureBlue
                            }
                        )
                    }

                    // CORRECCIÓN: Se procesa la respuesta como un Map, no como una lista
                    val barData = comparativaData?.let {
                        val userMap = it.usuario
                        val globalMap = it.global
                        val allKeys = (userMap.keys + globalMap.keys).distinct()

                        allKeys.map { key ->
                            BarData(
                                label = key.replaceFirstChar { char -> char.uppercase() },
                                userValue = userMap[key]?.toFloat() ?: 0f,
                                globalValue = globalMap[key]?.toFloat() ?: 0f
                            )
                        }
                    } ?: emptyList()

                    _uiState.value = EstadisticasUiState(
                        amenazasUsuario = amenazasUsuario,
                        comparativaData = barData,
                        isLoading = false
                    )
                } else {
                    val errorMsg = "Error al cargar estadísticas: ${distribucionResponse.message()} / ${comparativaResponse.message()}"
                    _uiState.value = _uiState.value.copy(isLoading = false, error = errorMsg)
                    Log.e("EstadisticasViewModel", errorMsg)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Excepción: ${e.message}")
                Log.e("EstadisticasViewModel", "Excepción al cargar estadísticas", e)
            }
        }
    }
}