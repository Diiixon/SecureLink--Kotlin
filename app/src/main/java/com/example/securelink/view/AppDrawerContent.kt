package com.example.securelink.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securelink.R
import com.example.securelink.model.Data.AppDatabase
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.repository.AuthRepository
import com.example.securelink.ui.theme.DarkBlue
import com.example.securelink.ui.theme.DarkTeal
import com.example.securelink.ui.theme.Gold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Define los elementos del menú (texto y ruta de navegación asociada).
private val menuItems = listOf(
    "Analizador" to "analyzer_internal",
    "Estadísticas" to "estadisticas_internal",
    "Aprende" to "learn_internal",
    "Perfil" to "perfil_internal"
)

// Composable que define el contenido del menú de navegación lateral (Drawer).
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    internalNavController: NavController, // El controlador para la navegación interna de la app.
    mainNavController: NavController,     // El controlador principal para la navegación (ej: para volver al home).
    drawerState: DrawerState,             // El estado del drawer (abierto/cerrado).
    scope: CoroutineScope                 // El scope para lanzar corutinas (ej: para cerrar el drawer).
) {
    val context = LocalContext.current
    // Se instancia el repositorio aquí para poder llamar a la función de cerrar sesión.
    // En apps más grandes, esto se manejaría con inyección de dependencias (Hilt).
    val authRepository = AuthRepository(
        AppDatabase.getDatabase(context).usuarioDao(),
        SessionManager(context)
    )

    ModalDrawerSheet(
        drawerContainerColor = DarkTeal
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.securelink_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Start)
                    .padding(bottom = 32.dp)
            )

            // Itera sobre la lista de 'menuItems' para crear cada opción de navegación dinámicamente.
            menuItems.forEach { (title, route) ->
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .clickable {
                            // Navega a la ruta interna correspondiente.
                            internalNavController.navigate(route) {
                                popUpTo(internalNavController.graph.startDestinationId)
                                launchSingleTop = true // Evita apilar la misma pantalla varias veces.
                            }
                            // Cierra el menú después de hacer clic.
                            scope.launch { drawerState.close() }
                        }
                )
            }
            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón de cerrar sesión al final.

            // Botón para gestionar el cierre de sesión del usuario.
            Button(
                onClick = {
                    scope.launch {
                        drawerState.close() // Cierra el menú.
                        authRepository.cerrarSesion() // Llama al repositorio para invalidar la sesión.
                        // Navega de vuelta a la pantalla 'home', limpiando el stack de navegación anterior.
                        mainNavController.navigate("home") {
                            popUpTo(mainNavController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Gold)
            ) {
                Text(text = "Cerrar Sesión", color = DarkBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}