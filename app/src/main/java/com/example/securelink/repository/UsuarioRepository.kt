package com.example.securelink.repository

import android.content.Context
import com.example.securelink.model.Data.Usuario
import com.example.securelink.model.Data.SessionManager

/**
 * Repositorio para manejar operaciones de datos relacionadas con el usuario.
 *
 * @param context El contexto de la aplicación.
 */
class UsuarioRepository(private val context: Context) {

    private val sessionManager = SessionManager(context)

    /**
     * Obtiene el usuario actual.
     *
     * @return El usuario actual, o nulo si no hay ningún usuario conectado.
     */
    fun obtenerUsuarioActual(): Usuario? {
        val nombre = sessionManager.getUserName()
        val correo = sessionManager.getUserEmail()


        return if (!nombre.isNullOrEmpty() && !correo.isNullOrEmpty()) {
            Usuario(
                id = correo,
                nombre = nombre,
                correo = correo
            )
        } else {
            null
        }
    }
}