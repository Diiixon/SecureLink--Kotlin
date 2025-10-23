package com.example.securelink.viewmodel



import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.AppDatabase
import com.example.securelink.repository.AuthRepository
import com.example.securelink.model.RegistroUiState
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.UsuarioErrores
import com.example.securelink.model.Data.Usuario // Necesario para el _usuarioRegistrado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado.asStateFlow()

    // Estado para el resumen (Esto está bien como lo tenías)
    private val _usuarioRegistrado = MutableStateFlow<Usuario?>(null)
    val usuarioRegistrado: StateFlow<Usuario?> = _usuarioRegistrado

    private val authRepository: AuthRepository

    init {
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        val sessionManager = SessionManager(application)
        authRepository = AuthRepository(usuarioDao, sessionManager)
    }

    // --- Funciones de actualización ---
    fun onNombreUsuarioChange(valor: String) {
        _estado.update { it.copy(nombreUsuario = valor, errores = it.errores.copy(NombreUsuario = null)) }
    }
    fun onCorreoElectronicoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, errores = it.errores.copy(CorreoElectronico = null)) }
    }
    fun onContrasenaChange(valor: String) {
        _estado.update { it.copy(contrasena = valor, errores = it.errores.copy(Contrasena = null)) }
    }
    fun onContrasenaConfirmadaChange(valor: String) {
        _estado.update { it.copy(contrasenaConfirmada = valor, errores = it.errores.copy(ContrasenaConfirmada = null)) }
    }

    // --- Lógica de Validación (idéntica a la tuya) ---
    private fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            NombreUsuario = if (estadoActual.nombreUsuario.isBlank()) "Campo obligatorio" else null,
            CorreoElectronico = if (!estadoActual.correoElectronico.contains("@")) "Correo Inválido" else null,
            Contrasena = if (estadoActual.contrasena.length < 8) "Debe tener al menos 8 caracteres" else null,
            ContrasenaConfirmada = if (estadoActual.contrasenaConfirmada != estadoActual.contrasena) "Las contraseñas no coinciden" else null
        )
        val hayErrores = listOfNotNull(errores.NombreUsuario, errores.CorreoElectronico, errores.Contrasena, errores.ContrasenaConfirmada).isNotEmpty()
        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    // --- Lógica de Registro (ahora usa el Repositorio) ---
    fun registrarUsuario(onRegistroExitoso: () -> Unit) {
        if (!validarFormulario()) {
            Log.w("RegistroViewModel", "Validación falló.")
            return
        }

        viewModelScope.launch {
            try {
                val estadoActual = _estado.value
                val usuarioGuardado = authRepository.registrarUsuario(
                    nombre = estadoActual.nombreUsuario,
                    correo = estadoActual.correoElectronico,
                    contrasena = estadoActual.contrasena
                )

                if (usuarioGuardado != null) {
                    // Éxito
                    _usuarioRegistrado.value = usuarioGuardado
                    _estado.update { it.copy(registroExitoso = true) } // Para el LaunchedEffect
                    onRegistroExitoso() // Para la navegación
                } else {
                    // Correo ya existe
                    _estado.update {
                        it.copy(errores = it.errores.copy(CorreoElectronico = "El correo ya está en uso"))
                    }
                }
            } catch (e: Exception) {
                Log.e("RegistroViewModel", "Error inesperado: ${e.message}")
                _estado.update {
                    it.copy(errores = it.errores.copy(CorreoElectronico = "Error inesperado en la BD"))
                }
            }
        }
    }

    fun resetRegistroExitoso() {
        _estado.update { it.copy(registroExitoso = false) }
    }
}