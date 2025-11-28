package com.example.securelink.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.securelink.model.AnalisisResultado
import com.example.securelink.viewmodel.AnalisisEstado
import com.example.securelink.viewmodel.AnalyzerViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class AnalyzerScreenStatesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun analyzerScreen_showsInicial_state() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val estadoFlow = MutableStateFlow<AnalisisEstado>(AnalisisEstado.Inicial)
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra el formulario inicial
        composeTestRule.onNodeWithText("Analiza cualquier enlace sospechoso").assertExists()
        composeTestRule.onNodeWithText("Ingresa o Pega Aquí el mensaje o link...").assertExists()
        composeTestRule.onNodeWithText("Escanear Código QR").assertExists()
    }

    @Test
    fun analyzerScreen_showsAnalizando_state() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val estadoFlow = MutableStateFlow<AnalisisEstado>(AnalisisEstado.Analizando)
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra el indicador de carga
        composeTestRule.onNodeWithText("Analizando...").assertExists()
    }

    @Test
    fun analyzerScreen_showsResultadoSeguro_state() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val resultadoSeguro = AnalisisResultado(
            url = "https://safe.com",
            peligro = "seguros",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
        )
        val estadoFlow = MutableStateFlow<AnalisisEstado>(
            AnalisisEstado.Resultado(listOf(resultadoSeguro))
        )
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra el resultado seguro
        composeTestRule.onNodeWithText("Enlace Seguro").assertExists()
        composeTestRule.onNodeWithText("No hemos encontrado ninguna amenaza. Puedes proceder con tranquilidad.").assertExists()
    }

    @Test
    fun analyzerScreen_showsResultadoBloqueado_state() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val resultadoBloqueado = AnalisisResultado(
            url = "https://malicious.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Phishing",
            imitaA = "Banco XYZ",
            detalles = null
        )
        val estadoFlow = MutableStateFlow<AnalisisEstado>(
            AnalisisEstado.Resultado(listOf(resultadoBloqueado))
        )
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra el resultado bloqueado
        composeTestRule.onNodeWithText("Peligro: Enlace Fraudulento").assertExists()
        composeTestRule.onNodeWithText("Hemos detectado que este sitio es malicioso. Evita interactuar con él.").assertExists()
    }

    @Test
    fun analyzerScreen_showsResultadoSospechoso_state() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val resultadoSospechoso = AnalisisResultado(
            url = "https://suspicious.com",
            peligro = "sospechosos",
            tipoAmenaza = "Phishing potencial",
            imitaA = "Amazon",
            detalles = null
        )
        val estadoFlow = MutableStateFlow<AnalisisEstado>(
            AnalisisEstado.Resultado(listOf(resultadoSospechoso))
        )
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra el resultado sospechoso
        composeTestRule.onNodeWithText("Sitio Sospechoso").assertExists()
        composeTestRule.onNodeWithText("Este enlace presenta características inusuales. Te recomendamos no ingresar datos personales.").assertExists()
    }

    @Test
    fun analyzerScreen_showsError_state() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val estadoFlow = MutableStateFlow<AnalisisEstado>(
            AnalisisEstado.Error("Error de conexión")
        )
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra el mensaje de error
        composeTestRule.onNodeWithText("Error de conexión").assertExists()
        composeTestRule.onNodeWithText("Reintentar").assertExists()
    }

    @Test
    fun analyzerScreen_showsResultadoWithImitaA() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val resultado = AnalisisResultado(
            url = "https://fake-bank.com",
            peligro = "bloqueadas",
            tipoAmenaza = "Phishing",
            imitaA = "Banco Nacional",
            detalles = null
        )
        val estadoFlow = MutableStateFlow<AnalisisEstado>(
            AnalisisEstado.Resultado(listOf(resultado))
        )
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra "Imita a:"
        composeTestRule.onNodeWithText("Imita a: Banco Nacional").assertExists()
    }

    @Test
    fun analyzerScreen_showsResultadoWithURL() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val resultado = AnalisisResultado(
            url = "https://test.com",
            peligro = "seguros",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
        )
        val estadoFlow = MutableStateFlow<AnalisisEstado>(
            AnalisisEstado.Resultado(listOf(resultado))
        )
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra la URL
        composeTestRule.onNodeWithText("URL: https://test.com").assertExists()
    }

    @Test
    fun analyzerScreen_showsAnalyzeAnotherButton() {
        val vm = mockk<AnalyzerViewModel>(relaxed = true)
        val resultado = AnalisisResultado(
            url = "https://test.com",
            peligro = "seguros",
            tipoAmenaza = null,
            imitaA = null,
            detalles = null
        )
        val estadoFlow = MutableStateFlow<AnalisisEstado>(
            AnalisisEstado.Resultado(listOf(resultado))
        )
        every { vm.estado } returns estadoFlow

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        // Verificar que se muestra el botón para analizar otro enlace
        composeTestRule.onNodeWithText("Analizar otro enlace").assertExists()
    }
}

