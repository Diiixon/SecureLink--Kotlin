package com.example.securelink.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.securelink.model.DonutSlice
import com.example.securelink.model.Report
import com.example.securelink.model.StatItem
import com.example.securelink.model.LearnItem
import com.example.securelink.ui.screens.ReporteCard
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class ViewComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun aprendeCard_showsTitleAndDescription() {
        val item = LearnItem(
            imageResId = com.example.securelink.R.drawable.ic_launcher_foreground,
            title = "TituloTest",
            description = "DescripcionTest"
        )

        composeTestRule.setContent {
            AprendeCard(learnItem = item, onClick = {})
        }

        composeTestRule.onNodeWithText("TituloTest").assertExists()
        composeTestRule.onNodeWithText("DescripcionTest").assertExists()
    }

    @Test
    fun appDrawer_showsMenuItemsAndCerrarSesion() {
        val navController = mockk<NavController>(relaxed = true)
        val drawerState = androidx.compose.material3.DrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
        val scope = CoroutineScope(Dispatchers.Unconfined)

        composeTestRule.setContent {
            AppDrawerContent(
                internalNavController = navController,
                mainNavController = navController,
                drawerState = drawerState,
                scope = scope
            )
        }

        // Los textos definidos en menuItems
        composeTestRule.onNodeWithText("Analizador").assertExists()
        composeTestRule.onNodeWithText("Estadísticas").assertExists()
        composeTestRule.onNodeWithText("Aprende").assertExists()
        composeTestRule.onNodeWithText("Perfil").assertExists()

        // Botón de cerrar sesión
        composeTestRule.onNodeWithText("Cerrar Sesión").assertExists()
    }

    @Test
    fun barChartCard_showsTitle() {
        val producer = ChartEntryModelProducer()

        composeTestRule.setContent {
            BarChartCard(modelProducer = producer)
        }

        composeTestRule.onNodeWithText("Comparativa Global").assertExists()
    }

    @Test
    fun donutChartCard_showsTitleAndLegend() {
        val slices = listOf(
            DonutSlice(label = "Seguros", value = 3f, color = Color.Green),
            DonutSlice(label = "Peligrosos", value = 1f, color = Color.Red)
        )

        composeTestRule.setContent {
            DonutChartCard(slices = slices)
        }

        composeTestRule.onNodeWithText("Tus Tipos de Amenazas").assertExists()
        composeTestRule.onNodeWithText("Seguros").assertExists()
        composeTestRule.onNodeWithText("Peligrosos").assertExists()
    }

    @Test
    fun statsCard_displayTexts() {
        val stat = StatItem(count = "99", description = "Pruebas")

        composeTestRule.setContent {
            StatsCard(stat = stat)
        }

        composeTestRule.onNodeWithText("99").assertExists()
        composeTestRule.onNodeWithText("Pruebas").assertExists()
    }

    @Test
    fun statsSection_displayTexts() {
        val stat = StatItem(count = "99", description = "Pruebas")

        composeTestRule.setContent {
            StatsSection(stats = listOf(stat))
        }

        composeTestRule.onNodeWithText("El Pulso de la Seguridad Digital").assertExists()
    }

    @Test
    fun reporteCard_seguro_renders() {
        val baseColors = Color.White
        val reportSeguro = Report(
            id = 1,
            url = "https://safe.test",
            peligro = "seguro",
            tipoAmenaza = null,
            createdAt = null,
            detalles = null,
            imitaA = null,
            userId = null
        )

        composeTestRule.setContent {
            ReporteCard(
                reporte = reportSeguro,
                colorFondoOscuro = baseColors,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.Black
            )
        }

        composeTestRule.onNodeWithText("https://safe.test").assertExists()
        composeTestRule.onNodeWithText("Sitio Desconocido").assertExists()
        composeTestRule.onNodeWithText("Ninguno").assertExists()
    }

    @Test
    fun reporteCard_peligroso_renders() {
        val baseColors = Color.White
        val reportPeligroso = Report(
            id = 2,
            url = "https://bad.test",
            peligro = "peligroso",
            tipoAmenaza = "malware",
            createdAt = null,
            detalles = null,
            imitaA = "Ejemplo",
            userId = null
        )

        composeTestRule.setContent {
            ReporteCard(
                reporte = reportPeligroso,
                colorFondoOscuro = baseColors,
                colorSeguro = Color.Green,
                colorPeligroso = Color.Red,
                colorSospechoso = Color.Yellow,
                colorTextoClaro = Color.Black
            )
        }

        composeTestRule.onNodeWithText("https://bad.test").assertExists()
        composeTestRule.onNodeWithText("Imita a: Ejemplo").assertExists()
        composeTestRule.onNodeWithText("malware").assertExists()
    }
}
