package com.example.securelink.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelink.ui.theme.SecureBlue
import com.example.securelink.ui.theme.SecureDarkTeal
import com.example.securelink.ui.theme.SecureGold
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

@Composable
fun BarChartCard(modelProducer: ChartEntryModelProducer) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SecureDarkTeal)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Comparativa Global",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Chart(
                modifier = Modifier.height(250.dp),
                chart = columnChart(
                    columns = remember(SecureBlue, SecureGold) {
                        listOf(
                            LineComponent(
                                color = SecureBlue.toArgb(),
                                thicknessDp = 12f,
                                // CORRECCIÓN DEFINITIVA: El parámetro es 'allPercent', no 'all'.
                                shape = Shapes.roundedCornerShape(allPercent = 50)
                            ),
                            LineComponent(
                                color = SecureGold.copy(alpha = 0.4f).toArgb(),
                                thicknessDp = 12f,
                                shape = Shapes.roundedCornerShape(allPercent = 50)
                            )
                        )
                    }
                ),
                chartModelProducer = modelProducer
            )
        }
    }
}
