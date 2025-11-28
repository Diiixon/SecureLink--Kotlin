package com.example.securelink.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.securelink.model.LearnItem
import com.example.securelink.model.StatItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun homeScreen_muestraLogoYBotonesPrincipales() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val navController = object : NavHostController(context) {}

        val stats = listOf(
            StatItem(count = "10K+", description = "Usuarios protegidos"),
            StatItem(count = "1M+", description = "Enlaces analizados")
        )
        val learnItems = emptyList<LearnItem>()

        composeRule.setContent {
            HomeScreen(stats = stats, navController = navController, learnItems = learnItems)
        }

        composeRule.onNodeWithContentDescription("Logo").assertIsDisplayed()
        composeRule.onNodeWithText("Tu primera línea de defensa...").assertIsDisplayed()
        composeRule.onNodeWithText("Crea tu Cuenta Gratis").assertIsDisplayed()
        composeRule.onNodeWithText("Inicia sesion").assertIsDisplayed()
    }
}
