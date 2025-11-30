package com.example.securelink.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.AnalisisResultado
import com.example.securelink.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Clase sellada que representa el estado del análisis.
 */
sealed class AnalisisEstado {
    /**
     * Estado inicial.
     */
    object Inicial : AnalisisEstado()

    /**
     * Estado cuando el análisis está en curso.
     */
    object Analizando : AnalisisEstado()

    /**
     * Estado cuando el análisis es exitoso.
     *
     * @property resultados La lista de resultados del análisis.
     */
    data class Resultado(val resultados: List<AnalisisResultado>) : AnalisisEstado()

    /**
     * Estado cuando ocurre un error durante el análisis.
     *
     * @property mensaje El mensaje de error.
     */
    data class Error(val mensaje: String) : AnalisisEstado()
}

/**
 * ViewModel para la pantalla del analizador.
 */
class AnalyzerViewModel : ViewModel() {

    private val _estado = MutableStateFlow<AnalisisEstado>(AnalisisEstado.Inicial)
    val estado: StateFlow<AnalisisEstado> = _estado

    // CORRECCIÓN: Apuntar al servicio de API de Análisis correcto
    private val apiService = RetrofitClient.analysisApiService

    /**
     * Analiza una URL.
     *
     * @param url La URL a analizar.
     * @param token El token de autenticación.
     */
    fun analizarUrl(url: String, token: String) {
        if (url.isBlank()) {
            _estado.value = AnalisisEstado.Error("Por favor, ingresa una URL válida")
            return
        }

        viewModelScope.launch {
            _estado.value = AnalisisEstado.Analizando
            Log.d("AnalisisViewModel", "Iniciando análisis de URL: $url")

            try {
                // El backend espera "textoAnalizar", no "url"
                val requestBody = mapOf("textoAnalizar" to url)
                Log.d("AnalisisViewModel", "Enviando petición con token: $token")

                val response = apiService.analizarUrl(
                    token = token,
                    request = requestBody
                )

                if (response.isSuccessful && response.body() != null) {
                    val resultados = response.body()!!
                    Log.d("AnalisisViewModel", "Análisis exitoso: ${resultados.size} resultados")
                    if (resultados.isNotEmpty()) {
                        _estado.value = AnalisisEstado.Resultado(resultados)
                    } else {
                        _estado.value = AnalisisEstado.Error("No se encontraron URLs en el texto")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AnalisisViewModel", "Error ${response.code()}: $errorBody")
                    _estado.value = AnalisisEstado.Error(
                        "Error al analizar: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("AnalisisViewModel", "Excepción: ${e.message}", e)
                _estado.value = AnalisisEstado.Error(
                    "Error de conexión: ${e.message ?: "No se pudo conectar"}"
                )
            }
        }
    }

    /**
     * Restablece el estado al estado inicial.
     */
    fun reiniciar() {
        _estado.value = AnalisisEstado.Inicial
    }
}