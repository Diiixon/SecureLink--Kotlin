package com.example.securelink.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.Data.AppDatabase
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.Data.Usuario
import com.example.securelink.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Modelo de datos que representa el estado de la pantalla de Perfil.
data class PerfilUiState(
    val usuario: Usuario? = null,
    val isEditingName: Boolean = false,       // Controla si se muestra el campo de edición de nombre.
    val newUsername: String = "",            // Almacena el nuevo nombre que el usuario está escribiendo.
    val showDeleteConfirmDialog: Boolean = false, // Controla la visibilidad del diálogo de confirmación.
    val accountDeleted: Boolean = false       // Se vuelve 'true' para que la UI navegue después de borrar la cuenta.
)

// ViewModel para la pantalla de Perfil.
class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository: AuthRepository

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
        val sessionManager = SessionManager(application)
        authRepository = AuthRepository(usuarioDao, sessionManager)

        // Se suscribe al Flow del usuario actual para que el estado de la UI
        // se actualice automáticamente si los datos del usuario cambian.
        viewModelScope.launch {
            authRepository.usuarioActual.collect { usuario ->
                _uiState.update {
                    it.copy(
                        usuario = usuario,
                        newUsername = usuario?.nombreUsuario ?: ""
                    )
                }
            }
        }
    }

    // Pone la UI en modo de edición de nombre.
    fun onEditNameClicked() {
        _uiState.update { it.copy(isEditingName = true) }
    }

    // Actualiza el estado con el nuevo nombre que se está escribiendo.
    fun onNewNameChange(name: String) {
        _uiState.update { it.copy(newUsername = name) }
    }

    // Guarda el nuevo nombre en la base de datos y sale del modo de edición.
    fun onSaveNameClicked() {
        viewModelScope.launch {
            authRepository.updateUsername(_uiState.value.newUsername)
            _uiState.update { it.copy(isEditingName = false) }
        }
    }

    // Inicia el flujo para eliminar la cuenta mostrando el diálogo de confirmación.
    fun onDeleteAccountClicked() {
        _uiState.update { it.copy(showDeleteConfirmDialog = true) }
    }

    // Oculta el diálogo de confirmación si el usuario cancela.
    fun onDismissDeleteDialog() {
        _uiState.update { it.copy(showDeleteConfirmDialog = false) }
    }

    // Llama al repositorio para eliminar la cuenta y actualiza el estado para la navegación.
    fun onConfirmDelete() {
        viewModelScope.launch {
            authRepository.deleteAccount()
            _uiState.update { it.copy(showDeleteConfirmDialog = false, accountDeleted = true) }
        }
    }

    // Resetea el estado de 'accountDeleted' después de que la navegación se ha completado.
    fun onAccountDeletionHandled() {
        _uiState.update { it.copy(accountDeleted = false) }
    }
}