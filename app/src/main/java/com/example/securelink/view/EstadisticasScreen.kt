package com.example.securelink.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun KpiCard(value: String, label: String, modifier: Modifier = Modifier){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF005F73))
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = value, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label, textAlign = TextAlign.Center, color = Color(0xFF94D2BD))
        }
    }
}

@Composable
fun EstadisticasScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Tu Panorama de Seguridad",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Datos actualizados en tiempo real",
            textAlign = TextAlign.Center,
            color = Color(0xFF94D2DB),
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KpiCard(value = "9", label = "Enlaces Analizados", modifier = Modifier.weight(1f))
            KpiCard(value = "2", label = "Amenazas Bloqueadas", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KpiCard(value = "Scam", label = "Amenaza Más Común", modifier = Modifier.weight(1f))
            KpiCard(value = "33.3%", label = "Taza de Navegacion Segura", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF005F73))
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                Text("Gráfico de Amenazas (proximamente)", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF005F73))
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                Text("Comparativa Global (proximamente)", color = Color.White)
            }
        }
    }
}