package com.example.securelink.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import com.example.securelink.model.LearnItem
import com.example.securelink.model.StatItem
import com.example.securelink.viewmodel.AnalyzerViewModel
import com.example.securelink.viewmodel.EstadisticasViewModel
import com.example.securelink.viewmodel.LearnViewModel
import com.example.securelink.viewmodel.LoginViewModel
import com.example.securelink.model.LoginUiState
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
class ViewScreensTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun learnScreen_displaysLearnItems() {
        val viewModel = LearnViewModel()

        composeTestRule.setContent {
            LearnScreen(viewModel = viewModel)
        }

        // One of the items' titles from repository should be present
        composeTestRule.onNodeWithText("Cómo Detectar Phishing").assertExists()
        composeTestRule.onNodeWithText("Contraseñas Seguras").assertExists()
    }

    @Test
    fun estadisticasScreen_displaysTitlesAndCharts() {
        val viewModel = EstadisticasViewModel()

        composeTestRule.setContent {
            EstadisticasScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Tu Panorama de Seguridad").assertExists()
        composeTestRule.onNodeWithText("Datos actualizados en tiempo real").assertExists()
    }

    @Test
    fun homeScreen_displaysBrandingAndButtons() {
        val navController = mockk<NavController>(relaxed = true)
        val stats = listOf(StatItem(count = "10", description = "Tests"))
        val learnItems = listOf(LearnItem(imageResId = com.example.securelink.R.drawable.ic_launcher_foreground, title = "T1", description = "D1"))

        composeTestRule.setContent {
            HomeScreen(stats = stats, navController = navController, learnItems = learnItems)
        }

        composeTestRule.onNodeWithText("Tu primera línea de defensa...").assertExists()
        composeTestRule.onNodeWithText("Crea tu Cuenta Gratis").assertExists()
        composeTestRule.onNodeWithText("Inicia sesion").assertExists()
    }

    @Test
    fun analyzerScreen_showsTitles() {
        val vm = AnalyzerViewModel()

        composeTestRule.setContent {
            AnalyzerScreen(analyzerViewModel = vm)
        }

        composeTestRule.onNodeWithText("Analiza cualquier enlace sospechoso").assertExists()
        composeTestRule.onNodeWithText("Pega cualquier enlace o mensaje. Analizamos su seguridad en segundos.").assertExists()
    }

    @Test
    fun loginScreen_showsTexts_and_buttons() {
        // Usar mock para evitar instanciar AndroidViewModel directamente
        val loginVm = mockk<LoginViewModel>(relaxed = true)
        val estadoFlow = MutableStateFlow(LoginUiState())
        every { loginVm.estado } returns estadoFlow

        val navController = mockk<NavController>(relaxed = true)

        composeTestRule.setContent {
            LoginScreen(navController = navController, viewModel = loginVm)
        }

        composeTestRule.onNodeWithText("Iniciar Sesión").assertExists()
        composeTestRule.onNodeWithText("Ingresar").assertExists()
        composeTestRule.onNodeWithText("¿Olvidaste tu contraseña?").assertExists()
    }
}
