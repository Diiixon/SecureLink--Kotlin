package com.example.securelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelink.ui.theme.SecureLinkTheme
import com.example.securelink.view.AnalyzerScreen
import com.example.securelink.view.EstadisticasScreen
import com.example.securelink.view.HomeScreen
import com.example.securelink.view.Formulario
import com.example.securelink.view.LoginScreen
import com.example.securelink.view.RecuperarScreen
import com.example.securelink.viewmodel.AnalyzerViewModel
import com.example.securelink.viewmodel.EstadisticasViewModel
import com.example.securelink.viewmodel.HomeViewModel
import com.example.securelink.viewmodel.LoginViewModel
import com.example.securelink.viewmodel.RecuperarViewModel
import com.example.securelink.viewmodel.RegistroViewModel


class MainActivity : ComponentActivity() {


    private val homeViewModel: HomeViewModel by viewModels()
    private val analyzerViewModel: AnalyzerViewModel by viewModels()
    private val estadisticasViewModel: EstadisticasViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureLinkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF001219) // Fondo oscuro de la app
                ) {
                    // 2. Crea el controlador de navegación (común para ambos)
                    val navController = rememberNavController()

                    // 3. Define el "mapa" de tu aplicación con NavHost
                    // Usamos "home" como ruta inicial, como tú lo tenías.
                    NavHost(navController = navController, startDestination = "home") {

                        // --- Ruta de INICIO (Tu versión, que es más completa) ---
                        composable("home") {
                            val stats by homeViewModel.stats.collectAsStateWithLifecycle()
                            val learnItems by homeViewModel.learnItems.collectAsStateWithLifecycle()

                            // Esta llamada a HomeScreen coincide con tu versión y es la correcta
                            HomeScreen(
                                navController = navController,
                                stats = stats,
                                learnItems = learnItems
                            )
                        }

                        // --- Ruta del ANALIZADOR (Tu versión) ---
                        composable("analyzer") {
                            AnalyzerScreen(
                                navController = navController,
                                viewModel = analyzerViewModel
                            )
                        }

                        // --- Ruta de ESTADÍSTICAS (Tu versión) ---
                        composable("estadisticas") {
                            EstadisticasScreen(navController = navController)
                        }

                        // --- Ruta de REGISTRO (Versión de Vicente) ---
                        composable("RegistroScreen") {
                            // Instancia el ViewModel solo para esta ruta
                            val registroViewModel: RegistroViewModel = viewModel()
                            Formulario(
                                navController = navController,
                                viewModel = registroViewModel
                            )
                        }

                        // --- Ruta de LOGIN (Versión de Vicente) ---
                        composable("Login") {
                            val loginViewModel: LoginViewModel = viewModel()
                            LoginScreen(
                                navController = navController,
                                viewModel = loginViewModel
                            )
                        }

                        // --- Ruta de RECUPERAR (Versión de Vicente) ---
                        composable("RecuperarScreen") {
                            val recuperarViewModel: RecuperarViewModel = viewModel()
                            RecuperarScreen(
                                navController = navController,
                                viewModel = recuperarViewModel
                            )
                        }

                        // --- Ruta de PERFIL (Versión de Vicente) ---
                        composable("PerfilScreen") {
                            // Placeholder temporal
                            Text(text = "Pantalla de Perfil", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
