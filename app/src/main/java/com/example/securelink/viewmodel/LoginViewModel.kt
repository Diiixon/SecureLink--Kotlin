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

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    private val authRepository: AuthRepository

    init {
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        val sessionManager = SessionManager(application)
        authRepository = AuthRepository(usuarioDao, sessionManager)
    }

    // --- Funciones de actualización ---
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, error = null) }
    }

    fun onContrasenaChange(valor: String) {
        _estado.update { it.copy(contrasena = valor, error = null) }
    }

    // --- Lógica de Login (ahora usa el Repositorio) ---
    fun iniciarSesion(onLoginExitoso: () -> Unit) {
        viewModelScope.launch {
            val estadoActual = _estado.value
            val usuario = authRepository.iniciarSesion(
                correo = estadoActual.correoElectronico,
                contrasena = estadoActual.contrasena
            )

            if (usuario != null) {
                // Éxito
                _estado.update { it.copy(loginExitoso = true) }
                onLoginExitoso()
            } else {
                // Fallo (correo o contraseña)
                // Comprobamos por qué falló para dar un mejor mensaje
                val existe = authRepository.correoExiste(estadoActual.correoElectronico)
                val errorMsg = if (!existe) "El correo no está registrado" else "Contraseña incorrecta"
                _estado.update { it.copy(error = errorMsg) }
            }
        }
    }
}