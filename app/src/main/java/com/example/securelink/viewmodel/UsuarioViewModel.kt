package com.example.securelink.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.securelink.model.UsuarioErrores
import com.example.securelink.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel(){


    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado

    fun onNombreUsuarioChange (valor : String) {
        _estado.update { it.copy(NombreUsuario = valor,errores = it.errores.copy(NombreUsuario = null))}
    }

    fun onCorreoElectronicoChange (valor : String) {
        _estado.update { it.copy(CorreoElectronico = valor,errores = it.errores.copy(CorreoElectronico = null))}
    }

    fun onContrasenaChange (valor : String) {
        _estado.update { it.copy(Contrasena = valor,errores = it.errores.copy(Contrasena = null))}
    }

    fun onContrasenaConfirmadaChange (valor : String) {
        _estado.update { it.copy(ContrasenaConfirmada = valor,errores = it.errores.copy(ContrasenaConfirmada = null))}
    }

    fun validarFormulario(): Boolean{

        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            NombreUsuario = if (estadoActual.NombreUsuario.isBlank())"Campo obligatorio" else null,
            CorreoElectronico = if (!estadoActual.CorreoElectronico.contains("@")) "Correo Invalido" else null,
            Contrasena = if (estadoActual.Contrasena.length < 8) "Debe tener al menos 8 caracteres" else null,
            ContrasenaConfirmada = if (estadoActual.ContrasenaConfirmada != estadoActual.Contrasena) "Las contraseñas no coinciden" else null
        )

        val hayErrores = listOfNotNull(

            errores.NombreUsuario,
            errores.CorreoElectronico,
            errores.Contrasena,
            errores.ContrasenaConfirmada
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

}