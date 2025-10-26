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
import com.example.securelink.view.MainAppScreen
import com.example.securelink.view.RecuperarScreen
import com.example.securelink.viewmodel.AnalyzerViewModel
import com.example.securelink.viewmodel.HomeViewModel
import com.example.securelink.viewmodel.LoginViewModel
import com.example.securelink.viewmodel.MainViewModel
import com.example.securelink.viewmodel.RecuperarViewModel
import com.example.securelink.viewmodel.RegistroViewModel

// Activity principal y punto de entrada de la aplicación.
class MainActivity : ComponentActivity() {

    // Instancia ViewModels a nivel de Activity para que sus datos sobrevivan a los cambios de pantalla.
    // Esto es útil para datos que se comparten o persisten entre diferentes secciones de la navegación.
    private val homeViewModel: HomeViewModel by viewModels()
    private val analyzerViewModel: AnalyzerViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    // Punto de entrada de la Activity. Aquí se configura la UI con Jetpack Compose.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureLinkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBlue
                ) {
                    val navController = rememberNavController()

                    // Configura el NavHost principal, responsable de la navegación de alto nivel
                    // (autenticación vs. app principal).
                    NavHost(navController = navController, startDestination = "home") {

                        // --- RUTAS DE AUTENTICACIÓN ---
                        // Ruta de la pantalla de inicio para usuarios no logueados.
                        composable("home") {
                            val stats by homeViewModel.stats.collectAsStateWithLifecycle()
                            val learnItems by homeViewModel.learnItems.collectAsStateWithLifecycle()
                            HomeScreen(
                                navController = navController,
                                stats = stats,
                                learnItems = learnItems
                            )
                        }

                        // Ruta para la pantalla de registro.
                        composable("RegistroScreen") {
                            Formulario(navController = navController, viewModel = viewModel<RegistroViewModel>())
                        }

                        // Ruta para la pantalla de inicio de sesión.
                        composable("Login") {
                            LoginScreen(navController = navController, viewModel = viewModel<LoginViewModel>())
                        }

                        // Ruta para la pantalla de recuperación de contraseña.
                        composable("RecuperarScreen") {
                            RecuperarScreen(navController = navController, viewModel = viewModel<RecuperarViewModel>())
                        }

                        // --- RUTA DE LA APP PRINCIPAL (POST-LOGIN) ---
                        // Ruta que carga la aplicación principal una vez que el usuario ha iniciado sesión.
                        // Se le pasan los ViewModels de nivel de Activity.
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