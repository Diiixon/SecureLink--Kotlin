package com.example.securelink.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.securelink.viewmodel.EstadisticasViewModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf

// Composable principal de la pantalla de Estadísticas.
@Composable
fun EstadisticasScreen(viewModel: EstadisticasViewModel = viewModel()) {
    // Obtiene el estado de la UI desde el ViewModel y reacciona a sus cambios.
    val uiState by viewModel.uiState.collectAsState()

    // Crea el productor de datos para el gráfico de barras (Vico).
    val barModelProducer = remember { ChartEntryModelProducer() }

    // Este efecto se ejecuta cuando los datos de la comparativa cambian.
    // Prepara y actualiza los datos para el gráfico de barras.
    LaunchedEffect(uiState.comparativaData) {
        val userEntries = uiState.comparativaData.mapIndexed { index, data -> entryOf(index, data.userValue) }
        val globalEntries = uiState.comparativaData.mapIndexed { index, data -> entryOf(index, data.globalValue) }
        barModelProducer.setEntries(userEntries, globalEntries)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Títulos de la pantalla ---
        Text(
            text = "Tu Panorama de Seguridad",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Datos actualizados en tiempo real",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // El gráfico de dona ahora usa Canvas y solo necesita la lista de "slices".
        DonutChartCard(
            slices = uiState.amenazasUsuario
        )

        Spacer(modifier = Modifier.height(16.dp))

        // El gráfico de barras sigue usando Vico y necesita su productor de datos.
        BarChartCard(modelProducer = barModelProducer)
    }
}