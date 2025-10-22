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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
// --- 1. IMPORTAR EL VIEWMODEL CORRECTO ---
import com.example.securelink.viewmodel.RecuperarViewModel

@Composable
fun RecuperarScreen(
    navController: NavController,
    // --- 2. CAMBIAR EL TIPO DE VIEWMODEL ---
    viewModel: RecuperarViewModel
) {
    val estado by viewModel.estado.collectAsState()

    // El estado del modal (showDialog) está bien como estado local
    var showDialog by remember { mutableStateOf(false) }

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
                    text = "Recupera tu Contraseña",
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ingresa tu correo y te enviaremos un enlace de recuperación.",
                    color = Mint,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                // --- 3. CAMBIAR ESTADO, HANDLER Y ERRORES ---
                OutlinedTextField(
                    value = estado.correoElectronico, // <-- CAMBIO
                    onValueChange = viewModel::onCorreoChange, // <-- CAMBIO
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(8.dp),
                    isError = estado.error != null, // <-- CAMBIO
                    supportingText = {
                        estado.error?.let { // <-- CAMBIO
                            Text(it, color = Red)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                // La lógica del botón ya era correcta para tu VM
                Button(
                    onClick = {
                        viewModel.solicitarRecuperacion(
                            onEnlaceEnviado = {
                                showDialog = true
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
                    )
                ) {
                    Text("Enviar Enlace", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                ClickableText(
                    text = AnnotatedString("¿Recordaste tu contraseña? Inicia Sesión"),
                    style = TextStyle(
                        color = Mint,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // --- 4. USAR LA FUNCIÓN onModalDismissed DEL VIEWMODEL ---
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    viewModel.onModalDismissed() // <-- CAMBIO
                },
                title = { Text("Correo Enviado") },
                text = {
                    Text("Se ha enviado un enlace de recuperación a ${estado.correoElectronico}.") // <-- CAMBIO
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            viewModel.onModalDismissed() // <-- CAMBIO
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}