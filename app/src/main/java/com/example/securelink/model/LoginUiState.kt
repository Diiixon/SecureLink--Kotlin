package com.example.securelink.model

// Modelo de datos que representa el estado de la pantalla de Login.
// Contiene todos los datos necesarios para que la UI se dibuje correctamente.
data class LoginUiState(
    val correoElectronico: String = "",
    val contrasena: String = "",
    val cargando: Boolean = false,
    val error: String? = null,
    val mensajeError: String? = null,
    val sesionIniciada: Boolean = false
)
