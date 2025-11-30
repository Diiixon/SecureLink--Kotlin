package com.example.securelink

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33])
class MainActivityAdditionalTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainActivity_initializesSuccessfully() {
        // Verifica que MainActivity se inicializa correctamente
        // La pantalla de inicio debe estar visible
        composeTestRule.waitForIdle()
        
        // Verificar que algún elemento de la UI está presente
        composeTestRule.onNodeWithText("Tu primera línea de defensa...").assertExists()
    }

    @Test
    fun mainActivity_showsLogo() {
        composeTestRule.waitForIdle()
        
        // Verifica que el logo/branding está presente
        composeTestRule.onNodeWithText("Analizamos enlaces...").assertExists()
    }

    @Test
    fun mainActivity_showsNavigationButtons() {
        composeTestRule.waitForIdle()
        
        // Verifica que los botones de navegación están presentes
        composeTestRule.onNodeWithText("Crea tu Cuenta Gratis").assertExists()
        composeTestRule.onNodeWithText("Inicia sesion").assertExists()
    }

    @Test
    fun mainActivity_showsStatsSection() {
        composeTestRule.waitForIdle()
        
        // Verifica que la sección de estadísticas está presente
        composeTestRule.onNodeWithText("El Pulso de la Seguridad Digital").assertExists()
    }

    @Test
    fun mainActivity_hasHomeScreenContent() {
        composeTestRule.waitForIdle()
        
        // Verifica múltiples elementos para asegurar que la pantalla está completamente cargada
        composeTestRule.onNodeWithText("Tu primera línea de defensa...").assertExists()
        composeTestRule.onNodeWithText("Crea tu Cuenta Gratis").assertExists()
        composeTestRule.onNodeWithText("El Pulso de la Seguridad Digital").assertExists()
    }
}

