package com.example.securelink.view

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.securelink.model.Report
import com.example.securelink.ui.screens.EstadisticaItem
import com.example.securelink.ui.screens.EstadisticasSeccion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class AdditionalComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun estadisticaItem_displaysCorrectly() {
        composeTestRule.setContent {
            EstadisticaItem(
                titulo = "Enlaces\nAnalizados",
                valor = "42",
                color = Color.White
            )
        }

        composeTestRule.onNodeWithText("42").assertExists()
        composeTestRule.onNodeWithText("Enlaces\nAnalizados").assertExists()
    }

    @Test
    fun estadisticasSeccion_displaysAllStats() {
        val reportes = listOf(
            Report(id = 1, url = "test1.com", peligro = "seguro", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 2, url = "test2.com", peligro = "peligroso", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 3, url = "test3.com", peligro = "sospechoso", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 4, url = "test4.com", peligro = "seguro", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null)
        )

        composeTestRule.setContent {
            EstadisticasSeccion(
                historialAnalisis = reportes,
                colorFondoOscuro = Color.DarkGray,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.White
            )
        }

        // Verificar que el componente se renderiza
        composeTestRule.waitForIdle()
    }

    @Test
    fun estadisticasSeccion_withEmptyList() {
        composeTestRule.setContent {
            EstadisticasSeccion(
                historialAnalisis = emptyList(),
                colorFondoOscuro = Color.DarkGray,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.White
            )
        }

        // Verificar que el componente se renderiza
        composeTestRule.waitForIdle()
    }

    @Test
    fun estadisticasSeccion_withMultipleReportTypes() {
        val reportes = listOf(
            Report(id = 1, url = "test1.com", peligro = "Seguro", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 2, url = "test2.com", peligro = "SEGUROS", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 3, url = "test3.com", peligro = "Peligroso", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 4, url = "test4.com", peligro = "BLOQUEADA", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 5, url = "test5.com", peligro = "Sospechoso", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null),
            Report(id = 6, url = "test6.com", peligro = "SOSPECHOSOS", tipoAmenaza = null, createdAt = null, detalles = null, imitaA = null, userId = null)
        )

        composeTestRule.setContent {
            EstadisticasSeccion(
                historialAnalisis = reportes,
                colorFondoOscuro = Color.DarkGray,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.White
            )
        }

        // Verificar que el componente se renderiza
        composeTestRule.waitForIdle()
    }

    @Test
    fun reporteCard_withSospechoso_renders() {
        val reporteSospechoso = Report(
            id = 1,
            url = "https://suspicious.test",
            peligro = "sospechoso",
            tipoAmenaza = "Phishing Potencial",
            createdAt = null,
            detalles = "Dominio reciente",
            imitaA = "Google",
            userId = null
        )

        composeTestRule.setContent {
            com.example.securelink.ui.screens.ReporteCard(
                reporte = reporteSospechoso,
                colorFondoOscuro = Color.DarkGray,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.White
            )
        }

        // Verificar que el componente se renderiza
        composeTestRule.waitForIdle()
    }

    @Test
    fun reporteCard_withNullValues_renders() {
        val reporteNulo = Report(
            id = 1,
            url = "https://unknown.test",
            peligro = null,
            tipoAmenaza = null,
            createdAt = null,
            detalles = null,
            imitaA = null,
            userId = null
        )

        composeTestRule.setContent {
            com.example.securelink.ui.screens.ReporteCard(
                reporte = reporteNulo,
                colorFondoOscuro = Color.DarkGray,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.White
            )
        }

        // Verificar que el componente se renderiza
        composeTestRule.waitForIdle()
    }

    @Test
    fun reporteCard_withBlockedStatus_renders() {
        val reporteBloqueado = Report(
            id = 1,
            url = "https://blocked.test",
            peligro = "bloqueada",
            tipoAmenaza = "Malware",
            createdAt = null,
            detalles = "Sitio bloqueado por autoridades",
            imitaA = null,
            userId = null
        )

        composeTestRule.setContent {
            com.example.securelink.ui.screens.ReporteCard(
                reporte = reporteBloqueado,
                colorFondoOscuro = Color.DarkGray,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.White
            )
        }

        // Verificar que el componente se renderiza
        composeTestRule.waitForIdle()
    }
}

