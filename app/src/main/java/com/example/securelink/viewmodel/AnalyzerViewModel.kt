package com.example.securelink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Modelo de datos que representa el estado de la pantalla del Analizador.
data class AnalyzerUiState(
    val textToAnalyze: String = "",       // El texto que el usuario introduce en el campo.
    val isLoading: Boolean = false,       // Se vuelve 'true' mientras se simula el análisis.
    val analysisResult: String? = null    // Guarda el resultado ("seguros", "sospechosos", etc.).
)

// ViewModel para la pantalla del Analizador.
class AnalyzerViewModel : ViewModel() {
    // StateFlow privado para gestionar el estado internamente.
    private val _uiState = MutableStateFlow(AnalyzerUiState())
    // StateFlow público y de solo lectura que la UI observará.
    val uiState = _uiState.asStateFlow()

    // Actualiza el estado con el nuevo texto del campo de entrada.
    fun onTextChanged(newText: String) {
        _uiState.update { it.copy(textToAnalyze = newText) }
    }

    // Inicia la simulación del análisis del enlace.
    fun onAnalyzeClicked() {
        if (_uiState.value.textToAnalyze.isBlank()) return

        viewModelScope.launch {
            // Pone la UI en estado de carga y limpia resultados anteriores.
            _uiState.update { it.copy(isLoading = true, analysisResult = null) }

            // Simula una llamada a una API o un proceso de análisis con un retraso.
            delay(2000)

            // Simula la obtención de un resultado aleatorio.
            val resultados = listOf("seguros", "sospechosos", "bloqueadas")
            val randomResult = resultados.random()

            // Actualiza la UI con el resultado y detiene el estado de carga.
            _uiState.update { it.copy(isLoading = false, analysisResult = randomResult, textToAnalyze = "") }
        }
    }

    // Resetea el estado para permitir un nuevo análisis.
    fun resetAnalysis() {
        _uiState.update {
            it.copy(
                isLoading = false,
                analysisResult = null
            )
        }
    }
}