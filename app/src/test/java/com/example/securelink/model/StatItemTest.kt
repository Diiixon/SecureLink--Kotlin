package com.example.securelink.model

import org.junit.Assert.*
import org.junit.Test

class StatItemTest {

    @Test
    fun `StatItem con todos los campos debe construirse correctamente`() {
        val statItem = StatItem(
            count = "150",
            description = "URLs Analizadas"
        )

        assertEquals("150", statItem.count)
        assertEquals("URLs Analizadas", statItem.description)
    }

    @Test
    fun `StatItem copy debe crear nueva instancia`() {
        val original = StatItem("150", "URLs Analizadas")
        val copy = original.copy()

        assertEquals(original.count, copy.count)
        assertEquals(original.description, copy.description)
    }

    @Test
    fun `StatItem copy con cambio de count`() {
        val original = StatItem("150", "URLs Analizadas")
        val copy = original.copy(count = "200")

        assertEquals("200", copy.count)
        assertEquals(original.description, copy.description)
    }

    @Test
    fun `StatItem copy con cambio de description`() {
        val original = StatItem("150", "URLs Analizadas")
        val copy = original.copy(description = "URLs Bloqueadas")

        assertEquals("URLs Bloqueadas", copy.description)
        assertEquals(original.count, copy.count)
    }

    @Test
    fun `StatItem equals debe retornar true para objetos iguales`() {
        val stat1 = StatItem("150", "URLs Analizadas")
        val stat2 = StatItem("150", "URLs Analizadas")

        assertEquals(stat1, stat2)
    }

    @Test
    fun `StatItem hashCode debe ser igual para objetos iguales`() {
        val stat1 = StatItem("150", "URLs Analizadas")
        val stat2 = StatItem("150", "URLs Analizadas")

        assertEquals(stat1.hashCode(), stat2.hashCode())
    }

    @Test
    fun `StatItem toString debe contener información relevante`() {
        val statItem = StatItem("150", "URLs Analizadas")
        val string = statItem.toString()

        assertTrue(string.contains("URLs Analizadas"))
        assertTrue(string.contains("150"))
    }

    @Test
    fun `StatItem con count cero`() {
        val statItem = StatItem("0", "Errores")

        assertEquals("0", statItem.count)
    }

    @Test
    fun `StatItem con description vacía`() {
        val statItem = StatItem("100", "")

        assertEquals("", statItem.description)
    }

    @Test
    fun `StatItem con count vacío`() {
        val statItem = StatItem("", "Test")

        assertEquals("", statItem.count)
    }

    @Test
    fun `StatItem copy con múltiples cambios`() {
        val original = StatItem("150", "URLs Analizadas")
        val copy = original.copy(
            count = "50",
            description = "URLs Bloqueadas"
        )

        assertEquals("50", copy.count)
        assertEquals("URLs Bloqueadas", copy.description)
    }

    @Test
    fun `StatItem con count numérico grande`() {
        val statItem = StatItem("1000000", "Total")

        assertEquals("1000000", statItem.count)
    }
}
