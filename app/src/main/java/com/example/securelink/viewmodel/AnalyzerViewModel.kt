package com.example.securelink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AnalyzerUiState(
    val textToAnalyze: String = "",
    val isLoading: Boolean = false,
    val analysisResult: String? = null
)

class AnalyzerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AnalyzerUiState())
    val uiState = _uiState.asStateFlow()

    // Función que la UI llamará cuando el texto cambie
    fun onTextChanged(newText: String) {
        _uiState.update { it.copy(textToAnalyze = newText) }
    }

    // Función que la UI llamará para iniciar el análisis
    fun onAnalyzeClicked() {
        if (_uiState.value.textToAnalyze.isBlank()) {
            // Aquí podrías mostrar un mensaje de error
            return
        }

        viewModelScope.launch {
            // Inicia la carga y limpia el resultado anterior
            _uiState.update { it.copy(isLoading = true, analysisResult = null) }

            // Simula un retraso de 2 segundos
            delay(2000)

            // Simula un resultado aleatorio
            val resultados = listOf("seguros", "sospechosos", "bloqueadas")
            val randomResult = resultados.random()

            // Detiene la carga y guarda el resultado
            _uiState.update { it.copy(isLoading = false, analysisResult = randomResult, textToAnalyze = "") }

            // TODO: Aquí irá la lógica para actualizar las estadísticas del usuario
        }
    }

    fun resetAnalysis() {
        _uiState.update {
            it.copy(
                isLoading = false,
                analysisResult = null
            )
        }
    }
}