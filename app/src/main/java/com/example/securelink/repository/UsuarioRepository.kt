package com.example.securelink.repository

import android.content.Context
import com.example.securelink.model.Data.Usuario
import com.example.securelink.model.Data.SessionManager

class UsuarioRepository(private val context: Context) {

    private val sessionManager = SessionManager(context)

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
