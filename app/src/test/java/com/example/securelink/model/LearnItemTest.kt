package com.example.securelink.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para LearnItem
 */
class LearnItemTest {

    @Test
    fun `LearnItem debería crearse con todos los campos`() {
        val item = LearnItem(
            imageResId = 123,
            title = "Phishing",
            description = "Aprende sobre phishing"
        )
        
        assertEquals(123, item.imageResId)
        assertEquals("Phishing", item.title)
        assertEquals("Aprende sobre phishing", item.description)
    }

    @Test
    fun `dos LearnItems con mismo contenido deberían ser iguales`() {
        val item1 = LearnItem(1, "Title", "Description")
        val item2 = LearnItem(1, "Title", "Description")
        
        assertEquals(item1, item2)
    }

    @Test
    fun `LearnItem debería tener título válido`() {
        val item = LearnItem(1, "Malware", "Info sobre malware")
        
        assertTrue(item.title.isNotBlank())
        assertTrue(item.title.length > 0)
    }

    @Test
    fun `LearnItem debería tener descripción válida`() {
        val item = LearnItem(1, "Scam", "Información sobre estafas")
        
        assertTrue(item.description.isNotBlank())
        assertTrue(item.description.length > 0)
    }

    @Test
    fun `LearnItem con imageResId positivo`() {
        val item = LearnItem(999, "Test", "Test description")
        
        assertTrue(item.imageResId > 0)
    }

    @Test
    fun `LearnItem copy debe crear nueva instancia`() {
        val original = LearnItem(123, "Phishing", "Descripción")
        val copy = original.copy()

        assertEquals(original.imageResId, copy.imageResId)
        assertEquals(original.title, copy.title)
    }

    @Test
    fun `LearnItem copy con cambio de title`() {
        val original = LearnItem(123, "Phishing", "Descripción")
        val copy = original.copy(title = "Malware")

        assertEquals("Malware", copy.title)
        assertEquals(original.imageResId, copy.imageResId)
    }

    @Test
    fun `LearnItem hashCode debe ser igual para objetos iguales`() {
        val item1 = LearnItem(123, "Phishing", "Descripción")
        val item2 = LearnItem(123, "Phishing", "Descripción")

        assertEquals(item1.hashCode(), item2.hashCode())
    }

    @Test
    fun `LearnItem toString debe contener información relevante`() {
        val item = LearnItem(123, "Phishing", "Descripción")
        val string = item.toString()

        assertTrue(string.contains("Phishing"))
        assertTrue(string.contains("123"))
    }

    @Test
    fun `LearnItem copy con múltiples cambios`() {
        val original = LearnItem(123, "Phishing", "Descripción original")
        val copy = original.copy(
            imageResId = 999,
            title = "Nuevo título",
            description = "Nueva descripción"
        )

        assertEquals(999, copy.imageResId)
        assertEquals("Nuevo título", copy.title)
        assertEquals("Nueva descripción", copy.description)
    }
}
