package com.example.securelink


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text // <-- AÑADIDO (para el placeholder)
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// import androidx.lifecycle.ViewModel // <-- ELIMINADO (No se usa)
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
// import androidx.lifecycle.viewmodel.viewModelFactory // <-- ELIMINADO (No se usa en este bloque)
// import androidx.navigation.NavHost // <-- ELIMINADO (Duplicado)
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelink.ui.theme.SecureLinkTheme
// --- IMPORTACIONES CORREGIDAS ---
// import com.example.securelink.view.Formulario // <-- ELIMINADO
import com.example.securelink.view.Formulario // <-- AÑADIDO (Coincide con tu archivo)
import com.example.securelink.view.HomeScreen
import com.example.securelink.view.LoginScreen

import com.example.securelink.view.RecuperarScreen
import com.example.securelink.viewmodel.HomeViewModel
// --- VIEWMODELS AÑADIDOS ---
import com.example.securelink.viewmodel.LoginViewModel
import com.example.securelink.viewmodel.RecuperarViewModel
import com.example.securelink.viewmodel.RegistroViewModel


class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureLinkTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF001219)) {

                    val stats by homeViewModel.stats.collectAsStateWithLifecycle()
                    // learnItems no se usa en HomeScreen, pero lo dejamos por si acaso
                    val learnItems by homeViewModel.learnItems.collectAsStateWithLifecycle()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "HomeScreen") {
                        composable("HomeScreen") {
                            HomeScreen(stats, navController)
                        }

                        // --- CAMBIO DE RUTA Y COMPOSABLE ---
                        composable("RegistroScreen") { // <-- CAMBIO (Nombre de ruta)
                            val registroViewModel: RegistroViewModel = viewModel()
                            Formulario( // <-- CAMBIO (Llama al Composable correcto)
                                navController = navController,
                                viewModel = registroViewModel
                            )
                        }

                        composable("Login") {
                            val loginViewModel: LoginViewModel = viewModel()
                            LoginScreen(
                                navController = navController,
                                viewModel = loginViewModel
                            )
                        }

                        composable("RecuperarScreen") {
                            val recuperarViewModel: RecuperarViewModel = viewModel()
                            RecuperarScreen(
                                navController = navController,
                                viewModel = recuperarViewModel
                            )
                        }

                        composable("PerfilScreen") {
                            // Añadimos un placeholder para que no esté vacío
                            Text(text = "Pantalla de Perfil", color = Color.White)
                            // Aquí irá tu lógica de PerfilViewModel cuando la implementes
                        }
                    }
                }
            }
        }
    }
}