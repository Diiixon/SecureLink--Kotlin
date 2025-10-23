package com.example.securelink.model
import com.example.securelink.model.UsuarioErrores


data class RegistroUiState(
    val nombreUsuario: String = "",
    val correoElectronico: String = "",
    val contrasena: String = "",
    val contrasenaConfirmada: String = "",

    val errores: UsuarioErrores = UsuarioErrores(),
    val registroExitoso: Boolean = false
)