package com.example.securelink.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securelink.R
import com.example.securelink.viewmodel.AnalyzerViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

// Helper data class para agrupar los datos visuales del resultado.
private data class ResultUIData(val title: String, val message: String, val color: Color, val icon: ImageVector?)

// Composable que muestra el resultado del análisis (seguro, sospechoso, etc.).
@Composable
fun AnalysisResult(result: String?) {
    if (result == null) return

    // Asigna un título, mensaje, color e icono según el string del resultado.
    val uiData = when (result) {
        "seguros" -> ResultUIData("Enlace Seguro", "No hemos encontrado ninguna amenaza. Puedes proceder con tranquilidad.", Color(0xFF2ECC71), Icons.Filled.CheckCircle)
        "sospechosos" -> ResultUIData("Sitio Sospechoso", "Este enlace presenta características inusuales. Te recomendamos no ingresar datos personales.", Color(0xFFF1C40F), Icons.Filled.Warning)
        "bloqueadas" -> ResultUIData("Peligro: Enlace Fraudulento", "Hemos detectado que este sitio es malicioso. Evita interactuar con él.", Color(0xFFE74C3C), Icons.Filled.Dangerous)
        else -> ResultUIData("", "", Color.Transparent, null)
    }

    if (uiData.icon != null) {
        // Layout que combina la barra de color, el icono y los textos del resultado.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min), // Asegura que el Row tenga altura para que la barra lateral la llene.
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(uiData.color)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = uiData.icon,
                contentDescription = uiData.title,
                tint = uiData.color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = uiData.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = uiData.color)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = uiData.message, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
        }
    }
}

// Composable principal de la pantalla del Analizador.
@Composable
fun AnalyzerScreen(
    navController: NavController,
    viewModel: AnalyzerViewModel
) {

    // Obtiene el estado actual de la UI desde el ViewModel.
    val uiState by viewModel.uiState.collectAsState()

    // Prepara el lanzador para la actividad de escaneo de QR y gestiona su resultado.
    val qrCodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        result.contents?.let {
            viewModel.onTextChanged(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- SECCIÓN DE TÍTULOS ---
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Analiza cualquier enlace sospechoso",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Pega cualquier enlace o mensaje. Analizamos su seguridad en segundos.",
            textAlign = TextAlign.Center,
            color = Color(0xFF94D2BD)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Contenedor principal para el formulario o el resultado.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF005F73), shape = MaterialTheme.shapes.medium)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Lógica principal: Muestra el formulario si no hay resultado, o el resultado si ya se analizó.
            if (uiState.analysisResult == null) {
                // --- FORMULARIO DE ENTRADA ---
                OutlinedTextField(
                    value = uiState.textToAnalyze,
                    onValueChange = { viewModel.onTextChanged(it) },
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    label = { Text("Ingresa o Pega Aquí el mensaje o link...") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color(0xFF001219),
                        unfocusedContainerColor = Color(0xFF001219),
                        focusedIndicatorColor = Color(0xFFEE9B00),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedLabelColor = Color(0xFFEE9B00),
                        unfocusedLabelColor = Color.Gray
                    )
                )
                Text(text = "o", color = Color.White, modifier = Modifier.padding(vertical = 16.dp))

                // Botón para iniciar el escáner de códigos QR.
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { qrCodeLauncher.launch(ScanOptions()) }
                        .background(Color(0xFF003F5C))
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_qr_code), contentDescription = "Icono de QR", tint = Color(0xFF94D2BD), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Escanear Código QR", color = Color(0xFF94D2BD), fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón para iniciar el análisis. Muestra un indicador de carga si está procesando.
                Button(
                    onClick = { viewModel.onAnalyzeClicked() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE9B00)),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading){
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black, strokeWidth = 2.dp)
                    } else {
                        Text(text = "Analizar ahora", color = Color.Black)
                    }
                }
            } else {
                // --- PANTALLA DE RESULTADO ---
                AnalysisResult(result = uiState.analysisResult)

                Spacer(modifier = Modifier.height(24.dp))

                // Botón para volver al formulario y analizar otro enlace.
                Button(
                    onClick = { viewModel.resetAnalysis() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE9B00)),
                ) {
                    Text(text = "Analizar otro enlace", color = Color.Black)
                }
            }
        }
    }
}
