package com.example.securelink.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelink.R
import com.example.securelink.ui.theme.DarkBlue
import com.example.securelink.viewmodel.AnalyzerViewModel
import com.example.securelink.viewmodel.EstadisticasViewModel
import com.example.securelink.viewmodel.LearnViewModel
import com.example.securelink.viewmodel.PerfilViewModel
import kotlinx.coroutines.launch

// Composable principal que estructura la aplicación una vez que el usuario ha iniciado sesión.
// Configura el menú lateral, la barra superior y el host de navegación interno.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    analyzerViewModel: AnalyzerViewModel,
    mainNavController: NavController, // El NavController principal, para la navegación global (ej: salir a 'home').
    mainViewModel: Any
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // El NavController secundario, para la navegación entre las pantallas internas de la app.
    val internalNavController = rememberNavController()

    // Componente raíz que gestiona el menú de navegación lateral.
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Define el contenido del menú lateral, pasándole ambos NavControllers.
            AppDrawerContent(
                internalNavController = internalNavController,
                mainNavController = mainNavController,
                drawerState = drawerState,
                scope = scope
            )
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        // Estructura de layout estándar con una barra superior y el contenido principal.
        Scaffold(
            topBar = {
                // Barra de navegación superior con el logo y el botón para abrir el menú.
                TopAppBar(
                    title = { Text("")},
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.securelink_logo),
                            contentDescription = "Logo",
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    },
                    actions = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir Menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue)
                )
            },
            containerColor = DarkBlue
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                // NavHost interno que gestiona la navegación entre las secciones de la aplicación.
                // Usa el 'internalNavController' para no interferir con la navegación principal.
                NavHost(
                    navController = internalNavController,
                    startDestination = "analyzer_internal"
                ) {
                    composable("analyzer_internal") {
                        AnalyzerScreen(
                            navController = internalNavController,
                            viewModel = analyzerViewModel
                        )
                    }
                    composable("estadisticas_internal") {
                        EstadisticasScreen(viewModel = viewModel<EstadisticasViewModel>())
                    }
                    composable("learn_internal") {
                        LearnScreen(viewModel = viewModel<LearnViewModel>())
                    }
                    composable("perfil_internal") {
                        // A la pantalla de perfil se le pasa el NavController principal para que
                        // pueda navegar a 'home' al eliminar o cerrar la sesión.
                        PerfilScreen(
                            navController = mainNavController, 
                            viewModel = viewModel<PerfilViewModel>()
                        )
                    }
                }
            }
        }
    }
}