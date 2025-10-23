package com.example.securelink.repository

import android.util.Log
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.Data.Usuario
import com.example.securelink.model.Data.UsuarioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

/**
 * AuthRepository centraliza el acceso a datos para la autenticación.
 * Es la ÚNICA fuente de verdad para los ViewModels.
 * Recibe el DAO y el SessionManager como dependencias.
 */
class AuthRepository(
    private val usuarioDao: UsuarioDao,
    private val sessionManager: SessionManager
) {

    /**
     * Intenta registrar un nuevo usuario.
     * Devuelve el Usuario si tiene éxito o null si el correo ya existe.
     * Lanza una excepción si hay un error de BD.
     */
    suspend fun registrarUsuario(
        nombre: String,
        correo: String,
        contrasena: String
    ): Usuario? {
        return withContext(Dispatchers.IO) {
            // 1. Comprobar si el correo ya existe
            val usuarioExistente = usuarioDao.getUsuarioPorCorreo(correo)
            if (usuarioExistente != null) {
                Log.w("AuthRepository", "Registro falló: Correo en uso.")
                return@withContext null // Devuelve null si el correo ya está en uso
            }

            // 2. Crear y guardar el nuevo usuario
            val nuevoUsuario = Usuario(
                nombreUsuario = nombre,
                correoElectronico = correo,
                contrasena = contrasena, // ¡Recuerda hashear esto en producción!
                fechaRegistro = LocalDate.now()
            )
            usuarioDao.insertarUsuario(nuevoUsuario)
            Log.d("AuthRepository", "Usuario registrado exitosamente.")

            // 3. Obtener el usuario guardado (con su ID) y guardar la sesión
            val usuarioGuardado = usuarioDao.getUsuarioPorCorreo(correo)
            usuarioGuardado?.let {
                sessionManager.guardarIdUsuario(it.id)
            }

            return@withContext usuarioGuardado
        }
    }

    /**
     * Intenta iniciar sesión.
     * Devuelve el Usuario si tiene éxito, o null si las credenciales son incorrectas.
     */
    suspend fun iniciarSesion(correo: String, contrasena: String): Usuario? {
        return withContext(Dispatchers.IO) {
            val usuario = usuarioDao.getUsuarioPorCorreo(correo)

            if (usuario == null) {
                Log.w("AuthRepository", "Login falló: Usuario no encontrado.")
                return@withContext null // Usuario no existe
            }

            if (usuario.contrasena != contrasena) {
                // ¡RECUERDA! Esto es inseguro. Deberías comparar hashes.
                Log.w("AuthRepository", "Login falló: Contraseña incorrecta.")
                return@withContext null // Contraseña incorrecta
            }

            // ¡Éxito! Guardar sesión y devolver usuario
            sessionManager.guardarIdUsuario(usuario.id)
            Log.d("AuthRepository", "Login exitoso.")
            return@withContext usuario
        }
    }

    /**
     * (Futuro) Comprueba si un correo existe para la recuperación.
     */
    suspend fun correoExiste(correo: String): Boolean {
        return withContext(Dispatchers.IO) {
            usuarioDao.getUsuarioPorCorreo(correo) != null
        }
    }
}