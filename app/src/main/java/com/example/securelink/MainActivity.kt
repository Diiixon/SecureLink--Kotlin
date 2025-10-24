package com.example.securelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelink.ui.theme.DarkBlue
import com.example.securelink.ui.theme.SecureLinkTheme
import com.example.securelink.view.Formulario
import com.example.securelink.view.HomeScreen
import com.example.securelink.view.LoginScreen
import com.example.securelink.view.MainAppScreen // <-- ASEGÚRATE DE IMPORTAR ESTO
import com.example.securelink.view.RecuperarScreen
import com.example.securelink.viewmodel.AnalyzerViewModel
import com.example.securelink.viewmodel.HomeViewModel
import com.example.securelink.viewmodel.LoginViewModel
import com.example.securelink.viewmodel.MainViewModel
import com.example.securelink.viewmodel.RecuperarViewModel
import com.example.securelink.viewmodel.RegistroViewModel


class MainActivity : ComponentActivity() {

    // Instanciamos los ViewModels que deben "sobrevivir" a la navegación
    private val homeViewModel: HomeViewModel by viewModels()
    private val analyzerViewModel: AnalyzerViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels() // <-- ViewModel para cerrar sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureLinkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBlue
                ) {
                    val navController = rememberNavController()

                    // Este es el NavHost PRINCIPAL (Gestor de Autenticación)
                    NavHost(navController = navController, startDestination = "home") {

                        // --- RUTAS DE AUTENTICACIÓN ---

                        composable("home") {
                            val stats by homeViewModel.stats.collectAsStateWithLifecycle()
                            val learnItems by homeViewModel.learnItems.collectAsStateWithLifecycle()

                            HomeScreen(
                                navController = navController,
                                stats = stats,
                                learnItems = learnItems
                            )
                        }

                        composable("RegistroScreen") {
                            val registroViewModel: RegistroViewModel = viewModel()
                            Formulario(
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

                        // --- RUTA DE LA APP PRINCIPAL (POST-LOGIN) ---
                        // ¡AÑADIMOS EL BLOQUE QUE FALTABA!
                        composable("MainApp") {
                            MainAppScreen(
                                analyzerViewModel = analyzerViewModel,
                                mainNavController = navController,
                                mainViewModel = mainViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}