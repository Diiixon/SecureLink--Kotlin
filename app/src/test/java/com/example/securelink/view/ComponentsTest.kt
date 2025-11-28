package com.example.securelink.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.securelink.R
import com.example.securelink.model.LearnItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class ComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `AprendeCard muestra el titulo y descripcion correctamente`() {
        val item = LearnItem(
            imageResId = R.drawable.ic_launcher_foreground, // Usa cualquier icono temporal
            title = "Titulo de Prueba",
            description = "Descripción de prueba"
        )

        // Cargamos SOLO el componente AprendeCard
        composeTestRule.setContent {
            AprendeCard(learnItem = item, onClick = {})
        }

        // Verificamos que el texto aparece. Al hacer esto, el código de AprendeCard se ha ejecutado.
        composeTestRule.onNodeWithText("Titulo de Prueba").assertExists()
        composeTestRule.onNodeWithText("Descripción de prueba").assertExists()
    }
}