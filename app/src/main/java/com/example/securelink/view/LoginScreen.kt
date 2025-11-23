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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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
import com.example.securelink.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val estado by viewModel.estado.collectAsState()

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
                    text = "Iniciar Sesión",
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Inicia sesion para acceder a tu perfil",
                    color = Mint,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Campo de correo
                OutlinedTextField(
                    value = estado.correoElectronico,
                    onValueChange = viewModel::onCorreoChange,
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp),
                    isError = estado.mensajeError != null,
                    enabled = !estado.cargando
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Campo de contraseña
                OutlinedTextField(
                    value = estado.contrasena,
                    onValueChange = viewModel::onContrasenaChange,
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp),
                    isError = estado.mensajeError != null,
                    enabled = !estado.cargando
                )

                // Texto de recuperación de contraseña
                ClickableText(
                    text = AnnotatedString("¿Olvidaste tu contraseña?"),
                    style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {
                        if (!estado.cargando) {
                            navController.navigate("RecuperarScreen")
                        }
                    },
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(54.dp))

                // Botón de login con indicador de carga
                Button(
                    onClick = {
                        viewModel.iniciarSesion(
                            onLoginExitoso = {
                                navController.navigate("MainApp") {
                                    popUpTo("home") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold,
                        contentColor = DarkBlue
                    ),
                    enabled = !estado.cargando
                ) {
                    if (estado.cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = DarkBlue,
                            strokeWidth = 3.dp
                        )
                    } else {
                        Text("Ingresar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mensaje de error
                estado.mensajeError?.let { mensaje ->
                    Text(
                        text = mensaje,
                        color = Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Texto de registro
                ClickableText(
                    text = AnnotatedString("¿No tienes una cuenta? Regístrate aqui"),
                    style = TextStyle(
                        color = Mint,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {
                        if (!estado.cargando) {
                            navController.navigate("RegistroScreen")
                        }
                    }
                )
            }
        }
    }
}
