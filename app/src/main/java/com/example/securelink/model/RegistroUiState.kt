package com.example.securelink.model
import com.example.securelink.model.UsuarioErrores


/**
 * Representa el estado de la pantalla de Registro.
 *
 * @property nombre El nombre del usuario.
 * @property correo El correo electrónico del usuario.
 * @property contrasena La contraseña del usuario.
 * @property confirmarContrasena La confirmación de la contraseña del usuario.
 * @property error Un mensaje de error, si lo hay.
 * @property registroExitoso Si el registro fue exitoso.
 * @property isLoading Si el proceso de registro está en curso.
 */
data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val error: String? = null,
    val registroExitoso: Boolean = false,
    val isLoading: Boolean = false
)