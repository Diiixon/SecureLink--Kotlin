package com.example.securelink.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.securelink.ui.theme.Mint
import com.example.securelink.ui.theme.White
import com.example.securelink.viewmodel.PerfilViewModel
import java.time.format.DateTimeFormatter

@Composable
fun PerfilInfoRow(label: String, value: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Mint,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = White,
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: PerfilViewModel = viewModel()
) {
    val usuario by viewModel.usuario.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Perfil",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (usuario == null) {
            CircularProgressIndicator(color = Mint)
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkTeal)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    PerfilInfoRow(label = "Nombre", value = usuario!!.nombreUsuario)
                    Divider(color = Mint.copy(alpha = 0.5f))
                    PerfilInfoRow(label = "Correo", value = usuario!!.correoElectronico)
                    Divider(color = Mint.copy(alpha = 0.5f))
                    PerfilInfoRow(
                        label = "Miembro desde:",
                        value = usuario!!.fechaRegistro.format(
                            DateTimeFormatter.ofPattern("dd MMMM, yyyy")
                        )
                    )
                }
            }
        }
    }
}