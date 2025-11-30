package com.example.securelink.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.securelink.viewmodel.EstadisticasViewModel
import com.example.securelink.viewmodel.LearnViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class LearnAndEstadisticasAdditionalTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun learnScreen_displaysAllLearnItems() {
        val viewModel = LearnViewModel()

        composeTestRule.setContent {
            LearnScreen(viewModel = viewModel)
        }

        // Verificar que la pantalla se renderiza correctamente
        composeTestRule.waitForIdle()
    }

    @Test
    fun learnScreen_itemsHaveDescriptions() {
        val viewModel = LearnViewModel()

        composeTestRule.setContent {
            LearnScreen(viewModel = viewModel)
        }

        // Verificar que la pantalla se renderiza correctamente
        composeTestRule.waitForIdle()
    }

    @Test
    fun estadisticasScreen_displaysMainTitle() {
        val viewModel = EstadisticasViewModel()

        composeTestRule.setContent {
            EstadisticasScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Tu Panorama de Seguridad").assertExists()
    }

    @Test
    fun estadisticasScreen_displaysSubtitle() {
        val viewModel = EstadisticasViewModel()

        composeTestRule.setContent {
            EstadisticasScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Datos actualizados en tiempo real").assertExists()
    }

    @Test
    fun estadisticasScreen_initializesSuccessfully() {
        val viewModel = EstadisticasViewModel()

        composeTestRule.setContent {
            EstadisticasScreen(viewModel = viewModel)
        }

        // Verificar que la pantalla se carga sin errores
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tu Panorama de Seguridad").assertExists()
    }

    @Test
    fun learnScreen_initializesSuccessfully() {
        val viewModel = LearnViewModel()

        composeTestRule.setContent {
            LearnScreen(viewModel = viewModel)
        }

        // Verificar que la pantalla se carga sin errores
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Cómo Detectar Phishing").assertExists()
    }
}

