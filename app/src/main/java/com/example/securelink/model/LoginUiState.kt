package com.example.securelink.model

data class LoginUiState(
    val correoElectronico: String = "",
    val contrasena: String = "",
    val error: String? = null, // Un solo mensaje de error es suficiente aquí
    val loginExitoso: Boolean = false
)