package com.example.securelink.repository

import android.content.Context
import com.example.securelink.model.Data.SessionManager
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UsuarioRepositoryTest {

    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    private lateinit var repository: UsuarioRepository

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        sessionManager = mockk(relaxed = true)
        
        // Mock SessionManager constructor
        mockkConstructor(SessionManager::class)
        every { anyConstructed<SessionManager>().getUserName() } returns "Test User"
        every { anyConstructed<SessionManager>().getUserEmail() } returns "test@example.com"
        
        repository = UsuarioRepository(context)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `obtenerUsuarioActual con datos válidos debe retornar Usuario`() {
        every { anyConstructed<SessionManager>().getUserName() } returns "Juan Pérez"
        every { anyConstructed<SessionManager>().getUserEmail() } returns "juan@example.com"
        
        val usuario = repository.obtenerUsuarioActual()
        
        assertNotNull(usuario)
        assertEquals("Juan Pérez", usuario!!.nombre)
        assertEquals("juan@example.com", usuario.correo)
        assertEquals("juan@example.com", usuario.id)
    }

    @Test
    fun `obtenerUsuarioActual con nombre nulo debe retornar null`() {
        every { anyConstructed<SessionManager>().getUserName() } returns null
        every { anyConstructed<SessionManager>().getUserEmail() } returns "test@example.com"
        
        val usuario = repository.obtenerUsuarioActual()
        
        assertNull(usuario)
    }

    @Test
    fun `obtenerUsuarioActual con email nulo debe retornar null`() {
        every { anyConstructed<SessionManager>().getUserName() } returns "Test User"
        every { anyConstructed<SessionManager>().getUserEmail() } returns null
        
        val usuario = repository.obtenerUsuarioActual()
        
        assertNull(usuario)
    }

    @Test
    fun `obtenerUsuarioActual con nombre vacío debe retornar null`() {
        every { anyConstructed<SessionManager>().getUserName() } returns ""
        every { anyConstructed<SessionManager>().getUserEmail() } returns "test@example.com"
        
        val usuario = repository.obtenerUsuarioActual()
        
        assertNull(usuario)
    }

    @Test
    fun `obtenerUsuarioActual con email vacío debe retornar null`() {
        every { anyConstructed<SessionManager>().getUserName() } returns "Test User"
        every { anyConstructed<SessionManager>().getUserEmail() } returns ""
        
        val usuario = repository.obtenerUsuarioActual()
        
        assertNull(usuario)
    }

    @Test
    fun `obtenerUsuarioActual con ambos null debe retornar null`() {
        every { anyConstructed<SessionManager>().getUserName() } returns null
        every { anyConstructed<SessionManager>().getUserEmail() } returns null
        
        val usuario = repository.obtenerUsuarioActual()
        
        assertNull(usuario)
    }

    @Test
    fun `obtenerUsuarioActual con ambos vacíos debe retornar null`() {
        every { anyConstructed<SessionManager>().getUserName() } returns ""
        every { anyConstructed<SessionManager>().getUserEmail() } returns ""
        
        val usuario = repository.obtenerUsuarioActual()
        
        assertNull(usuario)
    }

    @Test
    fun `obtenerUsuarioActual ejecuta getUserName`() {
        every { anyConstructed<SessionManager>().getUserName() } returns "Test"
        every { anyConstructed<SessionManager>().getUserEmail() } returns "test@example.com"
        
        repository.obtenerUsuarioActual()
        
        verify(exactly = 1) { anyConstructed<SessionManager>().getUserName() }
    }

    @Test
    fun `obtenerUsuarioActual ejecuta getUserEmail`() {
        every { anyConstructed<SessionManager>().getUserName() } returns "Test"
        every { anyConstructed<SessionManager>().getUserEmail() } returns "test@example.com"
        
        repository.obtenerUsuarioActual()
        
        verify(exactly = 1) { anyConstructed<SessionManager>().getUserEmail() }
    }
}
