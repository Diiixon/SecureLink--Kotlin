package com.example.securelink.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UsuarioErroresTest {

    @Test
    fun `crearUsuarioErrores_deberiaGuardarMensajes`() {
        // Given
        val nombreUsuario = "Nombre inválido"
        val correoElectronico = "Correo inválido"
        val contrasena = "Contraseña débil"
        val contrasenaConfirmada = "Las contraseñas no coinciden"

        // When
        val errores = UsuarioErrores(
            nombreUsuario = nombreUsuario,
            correoElectronico = correoElectronico,
            contrasena = contrasena,
            contrasenaConfirmada = contrasenaConfirmada
        )

        // Then
        assertEquals(nombreUsuario, errores.nombreUsuario)
        assertEquals(correoElectronico, errores.correoElectronico)
        assertEquals(contrasena, errores.contrasena)
        assertEquals(contrasenaConfirmada, errores.contrasenaConfirmada)
    }

    @Test
    fun `usuarioErrores_porDefectoDeberiaTenerCamposNulos`() {
        // When
        val errores = UsuarioErrores()

        // Then
        assertNull(errores.nombreUsuario)
        assertNull(errores.correoElectronico)
        assertNull(errores.contrasena)
        assertNull(errores.contrasenaConfirmada)
    }
}
