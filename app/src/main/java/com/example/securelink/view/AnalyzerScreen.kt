package com.example.securelink.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun AnalysisResult(result: String?) {
    if (result == null) return

    val (titulo, mensaje, color) = when (result){
        "seguros" -> Triple("Enlace Seguro", "No hemos encontrado ninguna amenaza.", Color(0xFF3498db))
        "sospechosos" -> Triple( "Sitio Sospechoso", "Recomendamos no ingresar datos personales.", Color(0xFFEE9B00))
        "bloqueadas" -> Triple("Peligro: Enlace Fraudulento", "Evita interactuar con este sitio", Color(0xFFE74C3C))
        else -> Triple("", "", Color.Transparent)
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = titulo, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = mensaje, color = Color(0xFF94D2BD), textAlign = TextAlign.Center)
    }
}

@Composable
fun AnalyzerScreen(
    navController: NavController,
    viewModel: AnalyzerViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF005F73), shape = MaterialTheme.shapes.medium)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.analysisResult == null) {
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

                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            val options = ScanOptions().apply {
                                setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                                setPrompt("Apunta la cámara al código QR")
                                setCameraId(0)
                                setBeepEnabled(true)
                                setOrientationLocked(true)
                            }
                            qrCodeLauncher.launch(options)
                        }
                        .background(Color(0xFF003F5C))
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_qr_code), contentDescription = "Icono de QR", tint = Color(0xFF94D2BD), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Escanear Código QR", color = Color(0xFF94D2BD), fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

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

                AnalysisResult(result = uiState.analysisResult)

                Spacer(modifier = Modifier.height(24.dp))

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