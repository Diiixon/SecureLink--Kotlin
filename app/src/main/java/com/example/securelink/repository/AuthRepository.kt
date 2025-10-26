package com.example.securelink.repository

import android.util.Log
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.Data.Usuario
import com.example.securelink.model.Data.UsuarioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import java.time.LocalDate

// Repositorio que centraliza la lógica de autenticación y el acceso a datos.
// Actúa como única fuente de verdad para los ViewModels.
class AuthRepository(
    private val usuarioDao: UsuarioDao,
    private val sessionManager: SessionManager
) {

    // Expone el usuario logueado actualmente como un Flow.
    // Reacciona a los cambios en el ID de sesión y obtiene los datos del usuario desde el DAO.
    val usuarioActual: Flow<Usuario?> = sessionManager.idUsuarioFlow.flatMapLatest { id ->
        if (id == null || id == -1) {
            flowOf(null) // Si no hay sesión, emite null.
        } else {
            usuarioDao.getUsuarioPorId(id) // Si hay sesión, observa al usuario en la BD.
        }
    }

    // Registra un nuevo usuario en la base de datos.
    suspend fun registrarUsuario(
        nombre: String,
        correo: String,
        contrasena: String
    ): Usuario? {
        return withContext(Dispatchers.IO) { // Ejecuta en un hilo de fondo.
            // Primero, comprueba si el correo ya está en uso para evitar duplicados.
            if (usuarioDao.getUsuarioPorCorreo(correo) != null) {
                return@withContext null
            }

            // Si no existe, crea el nuevo usuario, lo inserta e inicia su sesión.
            val nuevoUsuario = Usuario(
                nombreUsuario = nombre,
                correoElectronico = correo,
                contrasena = contrasena,
                fechaRegistro = LocalDate.now()
            )
            usuarioDao.insertarUsuario(nuevoUsuario)

            val usuarioGuardado = usuarioDao.getUsuarioPorCorreo(correo)
            usuarioGuardado?.let {
                sessionManager.guardarIdUsuario(it.id)
            }
            usuarioGuardado
        }
    }

    // Valida las credenciales de un usuario para iniciar sesión.
    suspend fun iniciarSesion(correo: String, contrasena: String): Usuario? {
        return withContext(Dispatchers.IO) {
            val usuario = usuarioDao.getUsuarioPorCorreo(correo)

            // Si el usuario no existe o la contraseña no coincide, devuelve null.
            if (usuario == null || usuario.contrasena != contrasena) {
                return@withContext null
            }

            // Si las credenciales son correctas, guarda el ID en el SessionManager.
            sessionManager.guardarIdUsuario(usuario.id)
            usuario
        }
    }

    // Cierra la sesión del usuario actual.
    suspend fun cerrarSesion() {
        withContext(Dispatchers.IO) {
            sessionManager.guardarIdUsuario(-1) // Invalida el ID de sesión.
        }
    }

    // Actualiza el nombre del usuario logueado.
    suspend fun updateUsername(newName: String) {
        withContext(Dispatchers.IO) {
            val userId = sessionManager.idUsuarioFlow.first() // Obtiene el ID de la sesión actual.
            if (userId != null && userId != -1) {
                usuarioDao.updateUsername(userId, newName)
            }
        }
    }

    // Elimina la cuenta del usuario actual.
    suspend fun deleteAccount() {
        withContext(Dispatchers.IO) {
            val userId = sessionManager.idUsuarioFlow.first()
            if (userId != null && userId != -1) {
                // Importante: primero se cierra la sesión para que la UI deje de observar los datos.
                sessionManager.guardarIdUsuario(-1)
                // Después, se elimina el registro de la base de datos de forma segura.
                usuarioDao.deleteUserById(userId)
            }
        }
    }

    // Comprueba si un correo ya está registrado en la base de datos.
    suspend fun correoExiste(correo: String): Boolean {
        return withContext(Dispatchers.IO) {
            usuarioDao.getUsuarioPorCorreo(correo) != null
        }
    }
}