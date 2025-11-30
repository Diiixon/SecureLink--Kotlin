package com.example.securelink.model

/**
 * Representa la respuesta de la API de perfil.
 *
 * @property id El ID del usuario.
 * @property name El nombre del usuario.
 * @property email El correo electrónico del usuario.
 * @property createdAt La fecha de creación del usuario.
 */
data class PerfilResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: String? = null
)