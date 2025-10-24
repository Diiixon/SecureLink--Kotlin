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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    analyzerViewModel: AnalyzerViewModel,
    mainNavController: NavController,
    mainViewModel: Any
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val internalNavController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                internalNavController = internalNavController,
                mainNavController = mainNavController,
                drawerState = drawerState,
                scope = scope
            )
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            topBar = {
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
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir Menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkBlue
                    )
                )
            },
            containerColor = DarkBlue
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
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
                        val statsViewModel: EstadisticasViewModel = viewModel()
                        EstadisticasScreen(navController = internalNavController)
                    }
                    composable("learn_internal") {
                        val learnViewModel: LearnViewModel = viewModel()
                        LearnScreen(
                            navController = internalNavController,
                            viewModel = learnViewModel
                        )
                    }
                    composable("perfil_internal") {
                        val perfilViewModel: PerfilViewModel = viewModel()
                        PerfilScreen(
                            navController = internalNavController,
                            viewModel = perfilViewModel
                        )
                    }
                }
            }
        }
    }
}
