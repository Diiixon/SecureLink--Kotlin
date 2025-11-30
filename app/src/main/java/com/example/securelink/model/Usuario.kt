package com.example.securelink.model

/**
 * Representa la solicitud para registrar un nuevo usuario.
 *
 * @property nombre El nombre del usuario.
 * @property email El correo electrónico del usuario.
 * @property password La contraseña del usuario.
 */
data class RegistroRequest(
    val nombre: String,
    val email: String,
    val password: String
)

/**
 * Representa la solicitud para iniciar sesión de un usuario.
 *
 * @property email El correo electrónico del usuario.
 * @property password La contraseña del usuario.
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * Representa la respuesta de la API de usuario.
 *
 * @property idUsuario El ID del usuario.
 * @property nombre El nombre del usuario.
 * @property correoElectronico El correo electrónico del usuario.
 */
data class UsuarioResponse(
    val idUsuario: Long? = null,
    val nombre: String? = null,
    val correoElectronico: String? = null
)

/**
 * Representa un objeto de transferencia de datos de usuario.
 *
 * @property id El ID del usuario.
 * @property nombre El nombre del usuario.
 * @property email El correo electrónico del usuario.
 */
data class UsuarioDto(
    val id: String,
    val nombre: String,
    val email: String
)