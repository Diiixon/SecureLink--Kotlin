package com.example.securelink.view


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securelink.viewmodel.AnalyzerViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AnalysisResult(result: String?) {
    if (result == null) return

    val (titulo, mensaje, color) = when (result){
        "seguros" -> Triple("Enlace Seguro", "No hemos encontrado ninguna amenaza.", Color(0xFF3498db))
        "sospechosos" -> Triple( "Sitio Sospechoso", "Recomendamos no ingresar datos personales.", Color(0xFFEE9B00))
        "bloqueadas" -> Triple("Peligro: Enlace Fraudulento", "Evita interactuar con este sitio", Color(0xFFE74C3C))
        else -> Triple("", "", Color.Transparent)
    }

    Spacer(modifier = Modifier.height(32.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF005F73))
    ) {
        Column(modifier = Modifier.padding(16.dp)){
            Text(text = titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = mensaje, color = Color(0xFF94D2BD))
        }
    }
}

@Composable
fun AnalyzerScreen(
    navController: NavController,
    viewModel: AnalyzerViewModel
    // Más adelante añadiremos parámetros del ViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // Título de la pantalla
        Text(
            text = "Analiza cualquier enlace sospechoso",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Pega cualquier enlace, mensaje o sube un archivo. Analizamos su seguridad en segundos.",
            textAlign = TextAlign.Center,
            color = Color(0xFF94D2BD) // Color menta
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Caja principal del analizador
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF005F73), shape = MaterialTheme.shapes.medium) // Fondo azulado
                .padding(24.dp)
        ) {
            // Campo de texto para pegar el enlace
            OutlinedTextField(
                value = uiState.textToAnalyze, // Esto lo conectaremos al ViewModel
                onValueChange = { viewModel.onTextChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
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
            Spacer(modifier = Modifier.height(24.dp))

            // Botón para analizar
            Button(
                onClick = { viewModel.onAnalyzeClicked() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE9B00)),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else{
                    Text(text = "Analizar ahora", color = Color.Black)
                }
            }
        }

        AnalysisResult(result = uiState.analysisResult)

    }
}