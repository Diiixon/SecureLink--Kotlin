package com.example.securelink.model

/**
 * Modelo de datos que representa el estado de la pantalla de Login.
 * Contiene todos los datos necesarios para que la UI se dibuje correctamente.
 *
 * @property correoElectronico El correo electrónico del usuario.
 * @property contrasena La contraseña del usuario.
 * @property cargando Si el proceso de inicio de sesión está en curso.
 * @property error Un mensaje de error, si lo hay.
 * @property mensajeError Un mensaje de error para mostrar al usuario.
 * @property sesionIniciada Si el usuario ha iniciado sesión.
 */
data class LoginUiState(
    val correoElectronico: String = "",
    val contrasena: String = "",
    val cargando: Boolean = false,
    val error: String? = null,
    val mensajeError: String? = null,
    val sesionIniciada: Boolean = false
)