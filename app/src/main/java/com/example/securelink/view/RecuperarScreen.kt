package com.example.securelink.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securelink.ui.theme.SecureDarkBlue
import com.example.securelink.ui.theme.SecureDarkTeal
import com.example.securelink.ui.theme.SecureGold
import com.example.securelink.ui.theme.Mint
import com.example.securelink.ui.theme.SecureRed
import com.example.securelink.ui.theme.Teal
import com.example.securelink.ui.theme.White
import com.example.securelink.viewmodel.RecuperarViewModel

@Composable
fun RecuperarScreen(
    navController: NavController,
    viewModel: RecuperarViewModel
) {
    val estado by viewModel.estado.collectAsState()

    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = White,
        unfocusedContainerColor = White,
        disabledContainerColor = White,
        focusedIndicatorColor = SecureGold, // CORREGIDO
        unfocusedIndicatorColor = Teal,
        cursorColor = SecureDarkBlue, // CORREGIDO
        focusedTextColor = SecureDarkBlue, // CORREGIDO
        unfocusedTextColor = SecureDarkBlue, // CORREGIDO
        focusedLabelColor = SecureGold, // CORREGIDO
        unfocusedLabelColor = SecureDarkTeal // CORREGIDO
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SecureDarkBlue // CORREGIDO
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
                    .background(SecureDarkTeal, shape = RoundedCornerShape(16.dp)), // CORREGIDO
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Recuperar Contraseña",
                    color = White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ingresa tu correo para recibir un enlace de recuperación",
                    color = Mint,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                if (estado.enlaceEnviado) {
                    Text(
                        text = "Si el correo está registrado, recibirás un enlace de recuperación en breve.",
                        color = Mint,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 40.dp)
                    )
                } else {
                    OutlinedTextField(
                        value = estado.correoElectronico,
                        onValueChange = viewModel::onCorreoChange,
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors,
                        shape = RoundedCornerShape(8.dp),
                        isError = estado.error != null,
                        supportingText = {
                            estado.error?.let {
                                Text(it, color = SecureRed) // CORREGIDO
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(54.dp))

                if (estado.enlaceEnviado) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SecureGold, contentColor = SecureDarkBlue) // CORREGIDO
                    ) {
                        Text("Volver", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                } else {
                    Button(
                        onClick = { viewModel.enviarCorreoRecuperacion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SecureGold, contentColor = SecureDarkBlue) // CORREGIDO
                    ) {
                        Text("Enviar Correo", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
