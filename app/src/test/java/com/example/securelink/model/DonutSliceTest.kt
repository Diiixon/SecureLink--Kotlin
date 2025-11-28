package com.example.securelink.model

import androidx.compose.ui.graphics.Color
import org.junit.Assert.*
import org.junit.Test

class DonutSliceTest {

    @Test
    fun `DonutSlice con todos los campos debe construirse correctamente`() {
        val slice = DonutSlice(
            label = "Seguros",
            value = 50.0f,
            color = Color.Green
        )

        assertEquals("Seguros", slice.label)
        assertEquals(50.0f, slice.value, 0.001f)
        assertEquals(Color.Green, slice.color)
    }

    @Test
    fun `DonutSlice copy debe crear nueva instancia`() {
        val original = DonutSlice("Seguros", 50.0f, Color.Green)
        val copy = original.copy()

        assertEquals(original.label, copy.label)
        assertEquals(original.value, copy.value, 0.001f)
        assertEquals(original.color, copy.color)
    }

    @Test
    fun `DonutSlice copy con cambio de label`() {
        val original = DonutSlice("Seguros", 50.0f, Color.Green)
        val copy = original.copy(label = "Peligrosos")

        assertEquals("Peligrosos", copy.label)
        assertEquals(original.value, copy.value, 0.001f)
    }

    @Test
    fun `DonutSlice copy con cambio de value`() {
        val original = DonutSlice("Seguros", 50.0f, Color.Green)
        val copy = original.copy(value = 75.0f)

        assertEquals(75.0f, copy.value, 0.001f)
        assertEquals(original.label, copy.label)
    }

    @Test
    fun `DonutSlice copy con cambio de color`() {
        val original = DonutSlice("Seguros", 50.0f, Color.Green)
        val copy = original.copy(color = Color.Red)

        assertEquals(Color.Red, copy.color)
        assertEquals(original.label, copy.label)
    }

    @Test
    fun `DonutSlice equals debe retornar true para objetos iguales`() {
        val slice1 = DonutSlice("Seguros", 50.0f, Color.Green)
        val slice2 = DonutSlice("Seguros", 50.0f, Color.Green)

        assertEquals(slice1, slice2)
    }

    @Test
    fun `DonutSlice hashCode debe ser igual para objetos iguales`() {
        val slice1 = DonutSlice("Seguros", 50.0f, Color.Green)
        val slice2 = DonutSlice("Seguros", 50.0f, Color.Green)

        assertEquals(slice1.hashCode(), slice2.hashCode())
    }

    @Test
    fun `DonutSlice toString debe contener información relevante`() {
        val slice = DonutSlice("Seguros", 50.0f, Color.Green)
        val string = slice.toString()

        assertTrue(string.contains("Seguros"))
        assertTrue(string.contains("50"))
    }

    @Test
    fun `DonutSlice con value cero`() {
        val slice = DonutSlice("Ninguno", 0.0f, Color.Gray)

        assertEquals(0.0f, slice.value, 0.001f)
    }

    @Test
    fun `DonutSlice con value máximo`() {
        val slice = DonutSlice("Total", 100.0f, Color.Blue)

        assertEquals(100.0f, slice.value, 0.001f)
    }

    @Test
    fun `DonutSlice copy con múltiples cambios`() {
        val original = DonutSlice("Seguros", 50.0f, Color.Green)
        val copy = original.copy(
            label = "Peligrosos",
            value = 25.0f,
            color = Color.Red
        )

        assertEquals("Peligrosos", copy.label)
        assertEquals(25.0f, copy.value, 0.001f)
        assertEquals(Color.Red, copy.color)
    }
}
