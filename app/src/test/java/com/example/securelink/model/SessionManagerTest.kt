package com.example.securelink.model.Data

import android.content.Context
import android.content.SharedPreferences
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SessionManagerTest {

    private lateinit var context: Context
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sessionManager: SessionManager

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        sharedPrefs = mockk(relaxed = true)
        editor = mockk(relaxed = true)

        every { context.getSharedPreferences(any(), any()) } returns sharedPrefs
        every { sharedPrefs.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.putInt(any(), any()) } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
        every { editor.apply() } just Runs
        every { editor.clear() } returns editor

        sessionManager = SessionManager(context)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `guardarSesionCompleta debe guardar todos los datos`() {
        sessionManager.guardarSesionCompleta(
            idUsuario = 123,
            nombre = "Juan Pérez",
            correo = "juan@test.com",
            token = "token123"
        )

        verify { editor.putInt("user_id", 123) }
        verify { editor.putString("user_name", "Juan Pérez") }
        verify { editor.putString("user_email", "juan@test.com") }
        verify { editor.putString("auth_token", "token123") }
        verify { editor.putBoolean("is_logged_in", true) }
        verify { editor.apply() }
    }

    @Test
    fun `hasActiveSession debe retornar true cuando hay token y está logueado`() {
        every { sharedPrefs.getString("auth_token", null) } returns "valid_token"
        every { sharedPrefs.getBoolean("is_logged_in", false) } returns true

        val result = sessionManager.hasActiveSession()

        assertTrue(result)
    }

    @Test
    fun `hasActiveSession debe retornar false cuando no hay token`() {
        every { sharedPrefs.getString("auth_token", null) } returns null
        every { sharedPrefs.getBoolean("is_logged_in", false) } returns true

        val result = sessionManager.hasActiveSession()

        assertFalse(result)
    }

    @Test
    fun `hasActiveSession debe retornar false cuando token está vacío`() {
        every { sharedPrefs.getString("auth_token", null) } returns ""
        every { sharedPrefs.getBoolean("is_logged_in", false) } returns true

        val result = sessionManager.hasActiveSession()

        assertFalse(result)
    }

    @Test
    fun `hasActiveSession debe retornar false cuando no está logueado`() {
        every { sharedPrefs.getString("auth_token", null) } returns "valid_token"
        every { sharedPrefs.getBoolean("is_logged_in", false) } returns false

        val result = sessionManager.hasActiveSession()

        assertFalse(result)
    }

    @Test
    fun `saveAuthToken debe guardar el token`() {
        sessionManager.saveAuthToken("nuevo_token")

        verify { editor.putString("auth_token", "nuevo_token") }
        verify { editor.apply() }
    }

    @Test
    fun `getAuthToken debe retornar el token guardado`() {
        every { sharedPrefs.getString("auth_token", null) } returns "mi_token"

        val token = sessionManager.getAuthToken()

        assertEquals("mi_token", token)
    }

    @Test
    fun `getAuthToken debe retornar null cuando no hay token`() {
        every { sharedPrefs.getString("auth_token", null) } returns null

        val token = sessionManager.getAuthToken()

        assertNull(token)
    }

    @Test
    fun `saveUserName debe guardar el nombre`() {
        sessionManager.saveUserName("María García")

        verify { editor.putString("user_name", "María García") }
        verify { editor.apply() }
    }

    @Test
    fun `getUserName debe retornar el nombre guardado`() {
        every { sharedPrefs.getString("user_name", null) } returns "Pedro López"

        val nombre = sessionManager.getUserName()

        assertEquals("Pedro López", nombre)
    }

    @Test
    fun `getUserName debe retornar null cuando no hay nombre`() {
        every { sharedPrefs.getString("user_name", null) } returns null

        val nombre = sessionManager.getUserName()

        assertNull(nombre)
    }

    @Test
    fun `saveUserEmail debe guardar el email`() {
        sessionManager.saveUserEmail("test@example.com")

        verify { editor.putString("user_email", "test@example.com") }
        verify { editor.apply() }
    }

    @Test
    fun `getUserEmail debe retornar el email guardado`() {
        every { sharedPrefs.getString("user_email", null) } returns "user@test.com"

        val email = sessionManager.getUserEmail()

        assertEquals("user@test.com", email)
    }

    @Test
    fun `getUserEmail debe retornar null cuando no hay email`() {
        every { sharedPrefs.getString("user_email", null) } returns null

        val email = sessionManager.getUserEmail()

        assertNull(email)
    }

    @Test
    fun `getUserId debe retornar el ID guardado`() {
        every { sharedPrefs.getInt("user_id", 0) } returns 456

        val userId = sessionManager.getUserId()

        assertEquals(456, userId)
    }

    @Test
    fun `getUserId debe retornar 0 cuando no hay ID`() {
        every { sharedPrefs.getInt("user_id", 0) } returns 0

        val userId = sessionManager.getUserId()

        assertEquals(0, userId)
    }

    @Test
    fun `setLoggedIn debe guardar el estado de login`() {
        sessionManager.setLoggedIn(true)

        verify { editor.putBoolean("is_logged_in", true) }
        verify { editor.apply() }
    }

    @Test
    fun `setLoggedIn false debe guardar false`() {
        sessionManager.setLoggedIn(false)

        verify { editor.putBoolean("is_logged_in", false) }
        verify { editor.apply() }
    }

    @Test
    fun `isLoggedIn debe retornar true cuando está logueado`() {
        every { sharedPrefs.getBoolean("is_logged_in", false) } returns true

        val loggedIn = sessionManager.isLoggedIn()

        assertTrue(loggedIn)
    }

    @Test
    fun `isLoggedIn debe retornar false cuando no está logueado`() {
        every { sharedPrefs.getBoolean("is_logged_in", false) } returns false

        val loggedIn = sessionManager.isLoggedIn()

        assertFalse(loggedIn)
    }

    @Test
    fun `clearSession debe limpiar todas las preferencias`() {
        sessionManager.clearSession()

        verify { editor.clear() }
        verify { editor.apply() }
    }

    @Test
    fun `guardarSesionCompleta con valores vacíos debe funcionar`() {
        sessionManager.guardarSesionCompleta(
            idUsuario = 0,
            nombre = "",
            correo = "",
            token = ""
        )

        verify { editor.putInt("user_id", 0) }
        verify { editor.putString("user_name", "") }
        verify { editor.putString("user_email", "") }
        verify { editor.putString("auth_token", "") }
    }
}
