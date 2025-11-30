package com.example.securelink.model

/**
 * CORREGIDO: Propiedades en camelCase para seguir la convención de Kotlin.
 *
 * @property nombreUsuario Mensaje de error para el nombre de usuario.
 * @property correoElectronico Mensaje de error para el correo electrónico.
 * @property contrasena Mensaje de error para la contraseña.
 * @property contrasenaConfirmada Mensaje de error para la confirmación de la contraseña.
 */
data class UsuarioErrores(
    val nombreUsuario: String? = null,
    val correoElectronico: String? = null,
    val contrasena: String? = null,
    val contrasenaConfirmada: String? = null
)