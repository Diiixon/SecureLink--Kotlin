package com.example.securelink.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.securelink.ui.theme.DarkBlue
import com.example.securelink.ui.theme.DarkTeal
import com.example.securelink.ui.theme.Gold
import com.example.securelink.ui.theme.Mint
import com.example.securelink.ui.theme.Red
import com.example.securelink.ui.theme.Teal
import com.example.securelink.ui.theme.White
import com.example.securelink.viewmodel.RegistroViewModel

@Composable
fun Formulario(
    navController: NavController,
    viewModel: RegistroViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()

    // ⭐ Navegar automáticamente cuando el registro sea exitoso
    LaunchedEffect(estado.registroExitoso) {
        if (estado.registroExitoso) {
            navController.navigate("Login") {
                popUpTo("Formulario") { inclusive = true }
            }
        }
    }

    // ⭐ Diálogo de error (si hay alguno)
    estado.error?.let { errorMsg ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error", fontWeight = FontWeight.Bold) },
            text = { Text(errorMsg) },
            confirmButton = {
                Button(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkBlue
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(16.dp))
                    .background(DarkTeal, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 40.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear una Cuenta",
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Únete para proteger tu navegación",
                    color = Mint,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                // ⭐ CAMBIO: Campos actualizados para coincidir con el nuevo estado
                OutlinedTextField(
                    value = estado.nombre,
                    onValueChange = viewModel::onNombreChange,
                    label = { Text("Nombre") },
                    enabled = !estado.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        focusedBorderColor = Gold,
                        unfocusedBorderColor = Teal,
                        cursorColor = DarkBlue,
                        focusedLabelColor = Gold,
                        unfocusedLabelColor = DarkTeal
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.correo,
                    onValueChange = viewModel::onCorreoChange,
                    label = { Text("Correo Electrónico") },
                    enabled = !estado.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        focusedBorderColor = Gold,
                        unfocusedBorderColor = Teal,
                        cursorColor = DarkBlue,
                        focusedLabelColor = Gold,
                        unfocusedLabelColor = DarkTeal
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.contrasena,
                    onValueChange = viewModel::onContrasenaChange,
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !estado.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        focusedBorderColor = Gold,
                        unfocusedBorderColor = Teal,
                        cursorColor = DarkBlue,
                        focusedLabelColor = Gold,
                        unfocusedLabelColor = DarkTeal
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.confirmarContrasena,
                    onValueChange = viewModel::onConfirmarContrasenaChange,
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !estado.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        focusedBorderColor = Gold,
                        unfocusedBorderColor = Teal,
                        cursorColor = DarkBlue,
                        focusedLabelColor = Gold,
                        unfocusedLabelColor = DarkTeal
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ⭐ Botón de registro con loading
                Button(
                    onClick = {
                        viewModel.registrarUsuario(
                            onRegistroExitoso = { /* La navegación se maneja con LaunchedEffect */ }
                        )
                    },
                    enabled = !estado.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold,
                        contentColor = DarkBlue
                    )
                ) {
                    if (estado.isLoading) {
                        CircularProgressIndicator(
                            color = DarkBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Registrar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    enabled = !estado.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Teal,
                        contentColor = White
                    )
                ) {
                    Text("Volver", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}
