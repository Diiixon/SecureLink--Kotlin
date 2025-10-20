package com.example.securelink.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
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
import com.example.securelink.viewmodel.UsuarioViewModel



@Composable
fun Formulario(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    // Contenedor principal que simula el `body` con el color de fondo
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkBlue
    ) {
        // Centra la tarjeta del formulario en la pantalla, como `auth-container`
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Contenedor de la tarjeta del formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(16.dp))
                    .background(DarkTeal, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 40.dp)
                    .verticalScroll(rememberScrollState()), // Permite scroll si el contenido es muy largo
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Encabezado del formulario,
                Text(
                    text = "Crear una Cuenta",
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Únete a nosotros para empezar.",
                    color = Mint,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Estilos personalizados para los campos de texto, como `input-group`
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

                // Campos de texto con los estilos aplicados
                OutlinedTextField(
                    value = estado.NombreUsuario,
                    onValueChange = viewModel::onNombreUsuarioChange,
                    label = { Text("Nombre Usuario") },
                    isError = estado.errores.NombreUsuario != null,
                    supportingText = {
                        estado.errores.NombreUsuario?.let {
                            Text(it, color = Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.CorreoElectronico,
                    onValueChange = viewModel::onCorreoElectronicoChange,
                    label = { Text("Correo Electrónico") },
                    isError = estado.errores.CorreoElectronico != null,
                    supportingText = {
                        estado.errores.CorreoElectronico?.let {
                            Text(it, color = Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.Contrasena,
                    onValueChange = viewModel::onContrasenaChange,
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = estado.errores.Contrasena != null,
                    supportingText = {
                        estado.errores.Contrasena?.let {
                            Text(it, color = Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = estado.ContrasenaConfirmada,
                    onValueChange = viewModel::onContrasenaConfirmadaChange,
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = estado.errores.ContrasenaConfirmada != null,
                    supportingText = {
                        estado.errores.ContrasenaConfirmada?.let {
                            Text(it, color = Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botones con estilo personalizado, como `submit-btn`
                Button(
                    onClick = {
                        if (viewModel.validarFormulario()) {
                            navController.navigate("resumen")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold,
                        contentColor = DarkBlue
                    )
                ) {
                    Text("Registrar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        navController.navigate("HomeScreen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Teal, // Un color secundario para diferenciarlo
                        contentColor = White
                    )
                ) {
                    Text("Volver", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}
