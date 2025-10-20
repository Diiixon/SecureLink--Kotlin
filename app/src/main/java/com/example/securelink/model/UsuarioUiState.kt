package com.example.securelink.model

data class UsuarioUiState(val NombreUsuario : String = "",
                          val CorreoElectronico : String = "",
                          val Contrasena : String = "",
                          val ContrasenaConfirmada : String = "",
                          val errores : UsuarioErrores = UsuarioErrores()
){


}