package com.example.securelink.model

import org.junit.Assert.*
import org.junit.Test

class BarDataTest {

    @Test
    fun `BarData con todos los campos debe construirse correctamente`() {
        val barData = BarData(
            label = "Sospechosos",
            userValue = 10.0f,
            globalValue = 25.0f
        )

        assertEquals("Sospechosos", barData.label)
        assertEquals(10.0f, barData.userValue, 0.001f)
        assertEquals(25.0f, barData.globalValue, 0.001f)
    }

    @Test
    fun `BarData copy debe crear nueva instancia`() {
        val original = BarData("Seguros", 50.0f, 60.0f)
        val copy = original.copy()

        assertEquals(original.label, copy.label)
        assertEquals(original.userValue, copy.userValue, 0.001f)
        assertEquals(original.globalValue, copy.globalValue, 0.001f)
    }

    @Test
    fun `BarData copy con cambio de label`() {
        val original = BarData("Seguros", 50.0f, 60.0f)
        val copy = original.copy(label = "Peligrosos")

        assertEquals("Peligrosos", copy.label)
        assertEquals(original.userValue, copy.userValue, 0.001f)
    }

    @Test
    fun `BarData copy con cambio de userValue`() {
        val original = BarData("Seguros", 50.0f, 60.0f)
        val copy = original.copy(userValue = 75.0f)

        assertEquals(75.0f, copy.userValue, 0.001f)
        assertEquals(original.label, copy.label)
    }

    @Test
    fun `BarData copy con cambio de globalValue`() {
        val original = BarData("Seguros", 50.0f, 60.0f)
        val copy = original.copy(globalValue = 80.0f)

        assertEquals(80.0f, copy.globalValue, 0.001f)
        assertEquals(original.label, copy.label)
    }

    @Test
    fun `BarData equals debe retornar true para objetos iguales`() {
        val bar1 = BarData("Seguros", 50.0f, 60.0f)
        val bar2 = BarData("Seguros", 50.0f, 60.0f)

        assertEquals(bar1, bar2)
    }

    @Test
    fun `BarData hashCode debe ser igual para objetos iguales`() {
        val bar1 = BarData("Seguros", 50.0f, 60.0f)
        val bar2 = BarData("Seguros", 50.0f, 60.0f)

        assertEquals(bar1.hashCode(), bar2.hashCode())
    }

    @Test
    fun `BarData toString debe contener información relevante`() {
        val barData = BarData("Seguros", 50.0f, 60.0f)
        val string = barData.toString()

        assertTrue(string.contains("Seguros"))
        assertTrue(string.contains("50"))
        assertTrue(string.contains("60"))
    }

    @Test
    fun `BarData con valores cero`() {
        val barData = BarData("Ninguno", 0.0f, 0.0f)

        assertEquals(0.0f, barData.userValue, 0.001f)
        assertEquals(0.0f, barData.globalValue, 0.001f)
    }

    @Test
    fun `BarData con userValue mayor que globalValue`() {
        val barData = BarData("Test", 100.0f, 50.0f)

        assertTrue(barData.userValue > barData.globalValue)
    }

    @Test
    fun `BarData con globalValue mayor que userValue`() {
        val barData = BarData("Test", 30.0f, 70.0f)

        assertTrue(barData.globalValue > barData.userValue)
    }

    @Test
    fun `BarData copy con múltiples cambios`() {
        val original = BarData("Seguros", 50.0f, 60.0f)
        val copy = original.copy(
            label = "Peligrosos",
            userValue = 25.0f,
            globalValue = 35.0f
        )

        assertEquals("Peligrosos", copy.label)
        assertEquals(25.0f, copy.userValue, 0.001f)
        assertEquals(35.0f, copy.globalValue, 0.001f)
    }

    @Test
    fun `BarData con valores idénticos`() {
        val barData = BarData("Test", 50.0f, 50.0f)

        assertEquals(barData.userValue, barData.globalValue, 0.001f)
    }

    @Test
    fun `BarData con label vacío`() {
        val barData = BarData("", 50.0f, 60.0f)

        assertEquals("", barData.label)
    }
}
