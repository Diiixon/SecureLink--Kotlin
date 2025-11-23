package com.example.securelink.model

// Estado SÓLO para la pantalla de Recuperación
data class RecuperarUiState(
    val correoElectronico: String = "",
    val error: String? = null,
    val enlaceEnviado: Boolean = false,
    val isLoading: Boolean = false
)