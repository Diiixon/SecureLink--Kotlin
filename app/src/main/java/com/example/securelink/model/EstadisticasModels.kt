package com.example.securelink.model

import androidx.compose.ui.graphics.Color

// Modelo de datos para una porción del gráfico de dona (o tarta).
data class DonutSlice(
    val label: String,    // El texto de la leyenda (ej: "Seguros")
    val value: Float,     // El valor numérico que representa la porción
    val color: Color      // El color de la porción en el gráfico
)

// Modelo de datos para las barras del gráfico de comparación.
data class BarData(
    val label: String,        // La etiqueta para el eje X (ej: "Sospechosos")
    val userValue: Float,     // El valor del usuario (ej: "Tus Reportes")
    val globalValue: Float    // El valor global para la comparación (simulado)
)
