package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.AppDatabase
import com.example.securelink.repository.AuthRepository
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel para la pantalla de Login.
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // StateFlow privado para gestionar el estado internamente.
    private val _estado = MutableStateFlow(LoginUiState())
    // StateFlow público que la UI observará para reaccionar a los cambios.
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    private val authRepository: AuthRepository

    // Prepara el repositorio de autenticación al iniciar el ViewModel.
    init {
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        val sessionManager = SessionManager(application)
        authRepository = AuthRepository(usuarioDao, sessionManager)
    }

    // Actualiza el estado con el nuevo valor del campo de correo.
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, error = null) } // Limpia errores anteriores.
    }

    // Actualiza el estado con el nuevo valor del campo de contraseña.
    fun onContrasenaChange(valor: String) {
        _estado.update { it.copy(contrasena = valor, error = null) } // Limpia errores anteriores.
    }

    // Inicia el proceso de inicio de sesión.
    fun iniciarSesion(onLoginExitoso: () -> Unit) {
        viewModelScope.launch {
            val estadoActual = _estado.value
            // Llama al repositorio para validar las credenciales.
            val usuario = authRepository.iniciarSesion(
                correo = estadoActual.correoElectronico,
                contrasena = estadoActual.contrasena
            )

            if (usuario != null) {
                // Si el login es exitoso, actualiza el estado y llama al callback para navegar.
                _estado.update { it.copy(loginExitoso = true) }
                onLoginExitoso()
            } else {
                // Si falla, comprueba la causa para dar un mensaje de error más específico.
                val existe = authRepository.correoExiste(estadoActual.correoElectronico)
                val errorMsg = if (!existe) "El correo no está registrado" else "Contraseña incorrecta"
                _estado.update { it.copy(error = errorMsg) }
            }
        }
    }
}