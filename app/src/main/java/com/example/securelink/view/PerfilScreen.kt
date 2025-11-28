package com.example.securelink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelink.model.Report
import com.example.securelink.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    perfilViewModel: PerfilViewModel,
    modifier: Modifier = Modifier
) {
    val usuario by perfilViewModel.usuario.collectAsState()
    val historialAnalisis by perfilViewModel.historialAnalisis.collectAsState()
    val isLoading by perfilViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        if (usuario == null && historialAnalisis.isEmpty()) {
            perfilViewModel.cargarDatos()
        }
    }

    val colorPrimario = Color(0xFF0A4D68)
    val colorSeguro = Color(0xFF088395)
    val colorPeligroso = Color(0xFFE63946)
    val colorSospechoso = Color(0xFFF2A71B)
    val colorFondoOscuro = Color(0xFF05445E)
    val colorTextoClaro = Color.White

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colorPrimario, colorFondoOscuro)
                )
            )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorSospechoso
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item { Spacer(modifier = Modifier.height(40.dp)) }

                item {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(colorFondoOscuro)
                            .padding(3.dp)
                            .clip(CircleShape)
                            .background(colorSospechoso.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(70.dp),
                            tint = colorSospechoso
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Text(
                        text = usuario?.nombre ?: "Usuario",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorTextoClaro
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                item {
                    Text(
                        text = usuario?.correo ?: "",
                        fontSize = 16.sp,
                        color = colorTextoClaro.copy(alpha = 0.7f)
                    )
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }

                item {
                    EstadisticasSeccion(
                        historialAnalisis = historialAnalisis,
                        colorFondoOscuro = colorFondoOscuro,
                        colorSeguro = colorSeguro,
                        colorPeligroso = colorPeligroso,
                        colorSospechoso = colorSospechoso,
                        colorTextoClaro = colorTextoClaro
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                if (historialAnalisis.isNotEmpty()) {
                    item {
                        Text(
                            text = "Enlaces Escaneados Recientemente",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorTextoClaro,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        )
                    }

                    items(historialAnalisis) { reporte ->
                        ReporteCard(
                            reporte = reporte,
                            colorFondoOscuro = colorFondoOscuro,
                            colorSeguro = colorSeguro,
                            colorPeligroso = colorPeligroso,
                            colorSospechoso = colorSospechoso,
                            colorTextoClaro = colorTextoClaro
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                } else {
                    item {
                        Text(
                            text = "No hay análisis en el historial",
                            fontSize = 16.sp,
                            color = colorTextoClaro.copy(alpha = 0.6f),
                            modifier = Modifier.padding(vertical = 32.dp)
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
fun EstadisticasSeccion(
    historialAnalisis: List<Report>,
    colorFondoOscuro: Color,
    colorSeguro: Color,
    colorPeligroso: Color,
    colorSospechoso: Color,
    colorTextoClaro: Color
) {
    val totalAnalisis = historialAnalisis.size
    val seguros = historialAnalisis.count { 
        val p = it.peligro?.lowercase() ?: ""
        p == "seguro" || p == "seguros"
    }
    val peligrosos = historialAnalisis.count { 
        val p = it.peligro?.lowercase() ?: ""
        p == "peligroso" || p == "peligrosos" || p == "bloqueada" || p == "bloqueadas"
    }
    val sospechosos = historialAnalisis.count { 
        val p = it.peligro?.lowercase() ?: ""
        p == "sospechoso" || p == "sospechosos"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondoOscuro.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EstadisticaItem(
                    titulo = "Enlaces\nAnalizados",
                    valor = totalAnalisis.toString(),
                    color = colorTextoClaro
                )
                EstadisticaItem(
                    titulo = "Enlaces\nSeguros",
                    valor = seguros.toString(),
                    color = colorSeguro
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EstadisticaItem(
                    titulo = "Enlaces\nPeligrosos",
                    valor = peligrosos.toString(),
                    color = colorPeligroso
                )
                EstadisticaItem(
                    titulo = "Enlaces\nSospechosos",
                    valor = sospechosos.toString(),
                    color = colorSospechoso
                )
            }
        }
    }
}

@Composable
fun EstadisticaItem(
    titulo: String,
    valor: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = valor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = titulo,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ReporteCard(
    reporte: Report,
    colorFondoOscuro: Color,
    colorSeguro: Color,
    colorPeligroso: Color,
    colorSospechoso: Color,
    colorTextoClaro: Color
) {
    // Determinar la categoría de peligro (puede venir en diferentes formatos)
    val peligroOriginal = reporte.peligro?.trim() ?: ""
    val peligro = when {
        peligroOriginal.equals("seguro", ignoreCase = true) || 
        peligroOriginal.equals("seguros", ignoreCase = true) ||
        peligroOriginal.equals("safe", ignoreCase = true) -> "seguro"
        peligroOriginal.equals("peligroso", ignoreCase = true) ||
        peligroOriginal.equals("peligrosos", ignoreCase = true) ||
        peligroOriginal.equals("bloqueada", ignoreCase = true) ||
        peligroOriginal.equals("bloqueadas", ignoreCase = true) ||
        peligroOriginal.equals("dangerous", ignoreCase = true) ||
        peligroOriginal.equals("malicious", ignoreCase = true) -> "peligroso"
        peligroOriginal.equals("sospechoso", ignoreCase = true) ||
        peligroOriginal.equals("sospechosos", ignoreCase = true) ||
        peligroOriginal.equals("suspicious", ignoreCase = true) -> "sospechoso"
        else -> "desconocido"
    }
    
    val colorEstado = when (peligro) {
        "seguro" -> colorSeguro
        "peligroso" -> colorPeligroso
        "sospechoso" -> colorSospechoso
        else -> Color.Gray
    }
    
    // Icono según la categoría
    val iconoEstado = when (peligro) {
        "seguro" -> Icons.Default.CheckCircle
        "peligroso" -> Icons.Default.Error
        "sospechoso" -> Icons.Default.Warning
        else -> Icons.Default.Warning
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondoOscuro.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = iconoEstado,
                contentDescription = peligro,
                tint = colorEstado,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reporte.url,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorTextoClaro,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                
                // Mostrar "Imita a:" si existe y no es N/A
                val imitaTexto = reporte.imitaA?.trim()
                val esValorInvalido = imitaTexto.isNullOrBlank() || 
                    imitaTexto.equals("null", ignoreCase = true) ||
                    imitaTexto.equals("N/A", ignoreCase = true) ||
                    imitaTexto.equals("Sitio Desconocido", ignoreCase = true)
                
                if (!esValorInvalido) {
                    Text(
                        text = "Imita a: $imitaTexto",
                        fontSize = 12.sp,
                        color = colorTextoClaro.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Text(
                        text = "Sitio Desconocido",
                        fontSize = 12.sp,
                        color = colorTextoClaro.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = colorEstado.copy(alpha = 0.2f)
            ) {
                // Mostrar tipo de amenaza del backend, o categoría si no hay
                val tipoAmenazaTexto = reporte.tipoAmenaza?.trim()
                val esValorInvalidoAmenaza = tipoAmenazaTexto.isNullOrBlank() || 
                    tipoAmenazaTexto.equals("null", ignoreCase = true) ||
                    tipoAmenazaTexto.equals("N/A", ignoreCase = true)
                
                val textoEstado = when {
                    !esValorInvalidoAmenaza -> tipoAmenazaTexto!!
                    peligro == "seguro" -> "Ninguno"
                    peligro == "peligroso" -> "Peligroso"
                    peligro == "sospechoso" -> "Sospechoso"
                    else -> "Desconocido"
                }
                
                Text(
                    text = textoEstado,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorEstado,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}
