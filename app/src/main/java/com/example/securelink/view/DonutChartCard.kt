package com.example.securelink.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelink.model.DonutSlice

// Composable reutilizable que muestra una tarjeta con un gráfico de dona personalizado y su leyenda.
@Composable
fun DonutChartCard(
    slices: List<DonutSlice>,
) {
    // Suma los valores de todas las porciones para calcular los porcentajes.
    val totalValue = slices.sumOf { it.value.toDouble() }.toFloat()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = com.example.securelink.ui.theme.DarkTeal)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tus Tipos de Amenazas",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            // Componente que dibuja el gráfico de dona.
            DonutChart(
                slices = slices,
                totalValue = totalValue,
                modifier = Modifier
                    .size(180.dp) 
                    .padding(vertical = 16.dp)
            )

            // Componente que muestra la leyenda del gráfico.
            DonutLegend(
                slices = slices,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

// Dibuja el gráfico de dona usando un Canvas.
@Composable
private fun DonutChart(
    slices: List<DonutSlice>,
    totalValue: Float,
    modifier: Modifier = Modifier
) {
    var startAngle = -90f // Empieza a dibujar desde la parte superior (12 en punto).

    Canvas(modifier = modifier) {
        slices.forEach { slice ->
            // Calcula el ángulo de cada porción en función de su valor.
            val sweepAngle = (slice.value / totalValue) * 360f
            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false, // false para dibujar un anillo en lugar de una tarta.
                style = Stroke(width = 40f, cap = StrokeCap.Butt) // Define el grosor del anillo.
            )
            // El ángulo de inicio de la siguiente porción es el final de la anterior.
            startAngle += sweepAngle
        }
    }
}

// Muestra la leyenda del gráfico con los colores y etiquetas de cada porción.
@Composable
private fun DonutLegend(
    slices: List<DonutSlice>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        slices.forEachIndexed { index, slice ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Pequeño círculo de color para la leyenda.
                Canvas(modifier = Modifier.size(10.dp)){
                    drawCircle(color = slice.color)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = slice.label,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
            // Añade espacio entre los elementos de la leyenda, excepto para el último.
            if (index < slices.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}
