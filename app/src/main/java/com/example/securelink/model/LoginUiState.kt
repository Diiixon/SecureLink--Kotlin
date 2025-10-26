package com.example.securelink.model

// Modelo de datos que representa el estado de la pantalla de Login.
// Contiene todos los datos necesarios para que la UI se dibuje correctamente.
data class LoginUiState(

    val correoElectronico: String = "",

    val contrasena: String = "",

    // Guarda un mensaje de error si la validación o el login fallan, para mostrarlo en la UI.
    val error: String? = null,

    // Se vuelve 'true' cuando el login es correcto, para que la UI pueda navegar a la pantalla principal.
    val loginExitoso: Boolean = false
)
