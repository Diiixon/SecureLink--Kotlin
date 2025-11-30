package com.example.securelink.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.securelink.viewmodel.EstadisticasViewModel
import com.example.securelink.viewmodel.EstadisticasViewModelFactory
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import com.example.securelink.ui.theme.SecureBlue
import com.example.securelink.ui.theme.SecureDarkBlue

@Composable
fun EstadisticasScreen(
    viewModel: EstadisticasViewModel = viewModel(factory = EstadisticasViewModelFactory(LocalContext.current))
) {
    val uiState by viewModel.uiState.collectAsState()
    val barModelProducer = remember { ChartEntryModelProducer() }

    LaunchedEffect(uiState.comparativaData) {
        if (uiState.comparativaData.isNotEmpty()) {
            val userEntries = uiState.comparativaData.mapIndexed { index, data -> entryOf(index.toFloat(), data.userValue) }
            val globalEntries = uiState.comparativaData.mapIndexed { index, data -> entryOf(index.toFloat(), data.globalValue) }
            barModelProducer.setEntries(userEntries, globalEntries)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecureDarkBlue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error al cargar las estadísticas: \n${uiState.error}",
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val totalUserReports = uiState.amenazasUsuario.sumOf { it.value.toInt() }
            val totalGlobalReports = uiState.comparativaData.sumOf { it.globalValue.toInt() }

            Text(
                text = "Tu Panorama de Seguridad",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Has aportado ", fontSize = 14.sp, color = Color.Gray)
                Text(text = "$totalUserReports", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SecureBlue)
                Text(text = " de ", fontSize = 14.sp, color = Color.Gray)
                Text(text = "$totalGlobalReports", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
                Text(text = " reportes a la comunidad", fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(32.dp))

            DonutChartCard(slices = uiState.amenazasUsuario)

            Spacer(modifier = Modifier.height(16.dp))

            BarChartCard(modelProducer = barModelProducer)
        }
    }
}