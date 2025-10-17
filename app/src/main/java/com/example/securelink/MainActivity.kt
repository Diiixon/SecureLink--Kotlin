package com.example.securelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelink.ui.theme.SecureLinkTheme
import com.example.securelink.view.AnalyzerScreen
import com.example.securelink.view.HomeScreen
import com.example.securelink.viewmodel.HomeViewModel
import com.example.securelink.viewmodel.AnalyzerViewModel
import com.example.securelink.view.EstadisticasScreen
import com.example.securelink.viewmodel.EstadisticasViewModel


class MainActivity : ComponentActivity() {

    // 1. Crea las instancias de tus ViewModels
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
                    // 2. Crea el controlador de navegación
                    val navController = rememberNavController()

                    // 3. Define el "mapa" de tu aplicación con NavHost
                    NavHost(navController = navController, startDestination = "home") {

                        // Ruta para la pantalla de inicio
                        composable("home") {
                            // Obtiene los datos del homeViewModel
                            val stats by homeViewModel.stats.collectAsStateWithLifecycle()
                            val learnItems by homeViewModel.learnItems.collectAsStateWithLifecycle()

                            // Llama a la HomeScreen y le pasa todo lo que necesita
                            HomeScreen(
                                navController = navController,
                                stats = stats,
                                learnItems = learnItems
                            )
                        }

                        // Ruta para la pantalla del analizador
                        composable("analyzer") {
                            // Llama a la AnalyzerScreen y le pasa lo que necesita
                            AnalyzerScreen(
                                navController = navController,
                                viewModel = analyzerViewModel
                            )
                        }

                        composable("estadisticas") {
                            EstadisticasScreen(navController = navController)
                        }

                        // Otras rutas...") {  }

                        // Aquí añadiremos más rutas en el futuro (login, registro, perfil, etc.)
                    }
                }
            }
        }
    }
}