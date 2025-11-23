package com.example.securelink.model
import com.example.securelink.model.UsuarioErrores


data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val error: String? = null,
    val registroExitoso: Boolean = false,
    val isLoading: Boolean = false
)