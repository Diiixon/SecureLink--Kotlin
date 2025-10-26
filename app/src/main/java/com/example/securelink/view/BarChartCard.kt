package com.example.securelink.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelink.ui.theme.DarkTeal
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

// Composable reutilizable que muestra una tarjeta con un gráfico de barras.
// Se usa para la sección "Comparativa Global" en la pantalla de estadísticas.
@Composable
fun BarChartCard(modelProducer: ChartEntryModelProducer) { // Recibe el productor de datos para el gráfico.
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkTeal)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Comparativa Global",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            // Componente de la librería Vico que dibuja el gráfico.
            Chart(
                modifier = Modifier.height(250.dp),
                chart = columnChart(), // Especifica que queremos un gráfico de columnas (barras).
                chartModelProducer = modelProducer // Conecta el gráfico con los datos del ViewModel.
            )
        }
    }
}
