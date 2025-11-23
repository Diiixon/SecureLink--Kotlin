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
import com.example.securelink.model.Data.SessionManager
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
    internalNavController: NavController,
    mainNavController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val context = LocalContext.current

    // ⭐ CAMBIO: Solo necesitas SessionManager, no AuthRepository
    val sessionManager = SessionManager(context)

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
                                launchSingleTop = true
                            }
                            // Cierra el menú después de hacer clic.
                            scope.launch { drawerState.close() }
                        }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ⭐ CAMBIO: Botón de cerrar sesión actualizado
            Button(
                onClick = {
                    scope.launch {
                        drawerState.close()

                        // Limpiar toda la sesión en DataStore
                        sessionManager.clearSession()

                        // Navegar al home y limpiar el stack
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
