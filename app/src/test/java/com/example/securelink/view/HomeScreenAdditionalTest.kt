package com.example.securelink.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import com.example.securelink.model.LearnItem
import com.example.securelink.model.StatItem
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class HomeScreenAdditionalTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_withMultipleStats_displaysAll() {
        val navController = mockk<NavController>(relaxed = true)
        val stats = listOf(
            StatItem(count = "100", description = "Enlaces Analizados"),
            StatItem(count = "95", description = "Enlaces Seguros"),
            StatItem(count = "5", description = "Amenazas Bloqueadas")
        )
        val learnItems = listOf(
            LearnItem(
                imageResId = com.example.securelink.R.drawable.ic_launcher_foreground,
                title = "Item 1",
                description = "Desc 1"
            )
        )

        composeTestRule.setContent {
            HomeScreen(stats = stats, navController = navController, learnItems = learnItems)
        }

        // Verificar que todas las estadísticas se muestran
        composeTestRule.onNodeWithText("100").assertExists()
        composeTestRule.onNodeWithText("Enlaces Analizados").assertExists()
        composeTestRule.onNodeWithText("95").assertExists()
        composeTestRule.onNodeWithText("Enlaces Seguros").assertExists()
        composeTestRule.onNodeWithText("5").assertExists()
        composeTestRule.onNodeWithText("Amenazas Bloqueadas").assertExists()
    }

    @Test
    fun homeScreen_withEmptyStats_stillDisplaysUI() {
        val navController = mockk<NavController>(relaxed = true)
        val stats = emptyList<StatItem>()
        val learnItems = emptyList<LearnItem>()

        composeTestRule.setContent {
            HomeScreen(stats = stats, navController = navController, learnItems = learnItems)
        }

        // Verificar que los elementos principales siguen mostrándose
        composeTestRule.onNodeWithText("Tu primera línea de defensa...").assertExists()
        composeTestRule.onNodeWithText("Crea tu Cuenta Gratis").assertExists()
        composeTestRule.onNodeWithText("El Pulso de la Seguridad Digital").assertExists()
    }

    @Test
    fun statsCard_withLargeNumbers_displaysCorrectly() {
        val stat = StatItem(count = "1,234,567", description = "Análisis Totales")

        composeTestRule.setContent {
            StatsCard(stat = stat)
        }

        composeTestRule.onNodeWithText("1,234,567").assertExists()
        composeTestRule.onNodeWithText("Análisis Totales").assertExists()
    }

    @Test
    fun statsSection_withSingleStat_displays() {
        val stats = listOf(
            StatItem(count = "1", description = "Test Único")
        )

        composeTestRule.setContent {
            StatsSection(stats = stats)
        }

        composeTestRule.onNodeWithText("El Pulso de la Seguridad Digital").assertExists()
        composeTestRule.onNodeWithText("1").assertExists()
        composeTestRule.onNodeWithText("Test Único").assertExists()
    }

    @Test
    fun statsSection_withEmptyList_stillShowsTitle() {
        val stats = emptyList<StatItem>()

        composeTestRule.setContent {
            StatsSection(stats = stats)
        }

        composeTestRule.onNodeWithText("El Pulso de la Seguridad Digital").assertExists()
    }

    @Test
    fun homeScreen_allButtonsArePresent() {
        val navController = mockk<NavController>(relaxed = true)
        val stats = listOf(StatItem(count = "10", description = "Tests"))
        val learnItems = listOf(
            LearnItem(
                imageResId = com.example.securelink.R.drawable.ic_launcher_foreground,
                title = "T1",
                description = "D1"
            )
        )

        composeTestRule.setContent {
            HomeScreen(stats = stats, navController = navController, learnItems = learnItems)
        }

        // Verificar ambos botones
        composeTestRule.onNodeWithText("Crea tu Cuenta Gratis").assertExists()
        composeTestRule.onNodeWithText("Inicia sesion").assertExists()
    }

    @Test
    fun homeScreen_displaysCorrectBranding() {
        val navController = mockk<NavController>(relaxed = true)
        val stats = listOf(StatItem(count = "10", description = "Tests"))
        val learnItems = emptyList<LearnItem>()

        composeTestRule.setContent {
            HomeScreen(stats = stats, navController = navController, learnItems = learnItems)
        }

        // Verificar textos de branding
        composeTestRule.onNodeWithText("Tu primera línea de defensa...").assertExists()
        composeTestRule.onNodeWithText("Analizamos enlaces...").assertExists()
    }
}

