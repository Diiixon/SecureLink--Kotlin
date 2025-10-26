package com.example.securelink.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.securelink.ui.theme.DarkTeal
import com.example.securelink.ui.theme.Gold
import com.example.securelink.ui.theme.Mint
import com.example.securelink.ui.theme.Red
import com.example.securelink.ui.theme.White
import com.example.securelink.viewmodel.PerfilViewModel
import java.time.format.DateTimeFormatter

// Composable reutilizable para mostrar una fila de información (etiqueta y valor).
@Composable
fun PerfilInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Mint, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(text = value, color = White, fontSize = 16.sp, textAlign = TextAlign.End, modifier = Modifier.weight(2f))
    }
}

// Composable principal de la pantalla de perfil de usuario.
@Composable
fun PerfilScreen(
    navController: NavController, // Se recibe el NavController principal.
    viewModel: PerfilViewModel = viewModel()
) {
    // Obtiene el estado de la UI desde el ViewModel y se suscribe a sus cambios.
    val uiState by viewModel.uiState.collectAsState()
    val usuario = uiState.usuario

    // Efecto que se lanza solo cuando 'accountDeleted' cambia a true.
    // Gestiona la navegación a 'home' después de que la cuenta ha sido eliminada.
    LaunchedEffect(uiState.accountDeleted) {
        if (uiState.accountDeleted) {
            navController.navigate("home") {
                popUpTo(navController.graph.id) { inclusive = true } // Limpia el stack de navegación.
            }
            viewModel.onAccountDeletionHandled() // Resetea el estado en el ViewModel.
        }
    }

    // Muestra un diálogo de confirmación si 'showDeleteConfirmDialog' es true.
    if (uiState.showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissDeleteDialog() },
            title = { Text("¿Eliminar cuenta?", fontWeight = FontWeight.Bold) },
            text = { Text("Esta acción es permanente y no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = { viewModel.onConfirmDelete() },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Confirmar", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.onDismissDeleteDialog() }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Perfil de Usuario",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Muestra un indicador de carga mientras se obtienen los datos del usuario.
        if (usuario == null) {
            CircularProgressIndicator(color = Mint)
        } else {
            // --- Tarjeta de Información del Usuario ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkTeal)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Lógica para mostrar el campo de texto de edición o el nombre del usuario.
                    if (uiState.isEditingName) {
                        OutlinedTextField(
                            value = uiState.newUsername,
                            onValueChange = { viewModel.onNewNameChange(it) },
                            label = { Text("Nuevo nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        PerfilInfoRow(label = "Nombre", value = usuario.nombreUsuario)
                    }
                    Divider(color = Mint.copy(alpha = 0.5f))
                    PerfilInfoRow(label = "Correo", value = usuario.correoElectronico)
                    Divider(color = Mint.copy(alpha = 0.5f))
                    PerfilInfoRow(
                        label = "Miembro desde:",
                        value = usuario.fechaRegistro.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy"))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón que cambia entre "Editar Nombre" y "Guardar Nombre" según el estado.
            val buttonText = if (uiState.isEditingName) "Guardar Nombre" else "Editar Nombre"
            val buttonOnClick = if (uiState.isEditingName) viewModel::onSaveNameClicked else viewModel::onEditNameClicked

            Button(
                onClick = buttonOnClick,
                colors = ButtonDefaults.buttonColors(containerColor = Gold),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText, color = DarkTeal, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón de eliminar al final.

            // Botón para iniciar el proceso de eliminación de la cuenta.
            Button(
                onClick = { viewModel.onDeleteAccountClicked() },
                colors = ButtonDefaults.buttonColors(containerColor = Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar Cuenta", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}