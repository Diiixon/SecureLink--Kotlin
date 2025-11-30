package com.example.securelink.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.securelink.MainActivity
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginScreen_muestraCamposBasicos() {
        composeRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
        composeRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }
}
