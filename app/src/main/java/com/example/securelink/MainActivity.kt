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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelink.ui.theme.SecureLinkTheme
import com.example.securelink.view.Formulario
import com.example.securelink.view.HomeScreen
import com.example.securelink.viewmodel.HomeViewModel
import com.example.securelink.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureLinkTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF001219)) {

                    val stats by homeViewModel.stats.collectAsStateWithLifecycle()
                    val learnItems by homeViewModel.learnItems.collectAsStateWithLifecycle()
                    val navController = rememberNavController()
                    val usuarioViewModel : UsuarioViewModel = viewModel()





                    NavHost(navController = navController, startDestination = "HomeScreen") {
                        composable("HomeScreen") {
                            HomeScreen(stats, navController)
                        }

                        composable("Registro") {
                            Formulario(navController = navController, viewModel = usuarioViewModel)
                        }
                    }


                }
            }
        }
    }
}
