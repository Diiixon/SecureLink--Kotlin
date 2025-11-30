package com.example.securelink.model

/**
 * Estado SÓLO para la pantalla de Recuperación.
 *
 * @property correoElectronico El correo electrónico del usuario.
 * @property error Un mensaje de error, si lo hay.
 * @property enlaceEnviado Si se ha enviado el enlace de recuperación.
 * @property isLoading Si el proceso de recuperación está en curso.
 */
data class RecuperarUiState(
    val correoElectronico: String = "",
    val error: String? = null,
    val enlaceEnviado: Boolean = false,
    val isLoading: Boolean = false
)