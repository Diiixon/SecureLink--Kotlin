package com.example.securelink.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.securelink.ui.theme.DarkBlue
import com.example.securelink.ui.theme.DarkTeal
import com.example.securelink.ui.theme.Gold
import com.example.securelink.ui.theme.Mint
import com.example.securelink.ui.theme.Red
import com.example.securelink.ui.theme.Teal
import com.example.securelink.ui.theme.White
import com.example.securelink.viewmodel.RegistroViewModel

// Composable principal para el formulario de registro de nuevos usuarios.
@Composable
fun Formulario(
    navController: NavController,
    viewModel: RegistroViewModel
) {
    // Obtiene el estado de la UI desde el ViewModel y se suscribe a sus cambios.
    val estado by viewModel.estado.collectAsState()

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
            // Contenedor de la tarjeta del formulario.
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

                val textFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    disabledContainerColor = White,
                    focusedIndicatorColor = Gold,
                    unfocusedIndicatorColor = Teal,
                    cursorColor = DarkBlue,
                    focusedTextColor = DarkBlue,
                    unfocusedTextColor = DarkBlue,
                    focusedLabelColor = Gold,
                    unfocusedLabelColor = DarkTeal
                )

                // --- Campos de texto del formulario ---
                // Cada campo está vinculado al estado del ViewModel y muestra errores de validación.
                OutlinedTextField(
                    value = estado.nombreUsuario,
                    onValueChange = viewModel::onNombreUsuarioChange,
                    label = { Text("Nombre Usuario") },
                    isError = estado.errores.nombreUsuario != null,
                    supportingText = { estado.errores.nombreUsuario?.let { Text(it, color = Red) } },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.correoElectronico,
                    onValueChange = viewModel::onCorreoElectronicoChange,
                    label = { Text("Correo Electrónico") },
                    isError = estado.errores.correoElectronico != null,
                    supportingText = { estado.errores.correoElectronico?.let { Text(it, color = Red) } },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.contrasena,
                    onValueChange = viewModel::onContrasenaChange,
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = estado.errores.contrasena != null,
                    supportingText = { estado.errores.contrasena?.let { Text(it, color = Red) } },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.contrasenaConfirmada,
                    onValueChange = viewModel::onContrasenaConfirmadaChange,
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = estado.errores.contrasenaConfirmada != null,
                    supportingText = { estado.errores.contrasenaConfirmada?.let { Text(it, color = Red) } },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón que llama a la función de registro en el ViewModel.
                // La navegación solo ocurre si el registro es exitoso, a través del callback.
                Button(
                    onClick = {
                        viewModel.registrarUsuario(
                            onRegistroExitoso = { navController.navigate("Login") }
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = DarkBlue)
                ) {
                    Text("Registrar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón para volver a la pantalla anterior en el stack de navegación.
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal, contentColor = White)
                ) {
                    Text("Volver", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}