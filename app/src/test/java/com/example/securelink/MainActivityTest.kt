package com.example.securelink

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// @RunWith(AndroidJUnit4::class) permite usar herramientas de Android en tests locales
@RunWith(AndroidJUnit4::class)
@Config(sdk = [33]) // Simula un Android 13
class MainActivityUnitTest {

    // Esta regla inicia la MainActivity antes de cada test
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `la app inicia y muestra la pantalla de home correctamente`() {
        // Al llegar aquí, MainActivity.onCreate() ya se ejecutó.
        // ¡Ya ganaste la cobertura de esos métodos!

        // Verificación simple para confirmar que se cargó la UI
        // Busca un texto que sepas que está en el HomeScreen
        composeTestRule.onNodeWithText("Tu primera línea de defensa...").assertExists()
    }
}