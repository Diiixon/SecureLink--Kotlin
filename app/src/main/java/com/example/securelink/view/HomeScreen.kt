package com.example.securelink.view

import com.example.securelink.model.StatItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelink.R
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import com.example.securelink.model.LearnItem
import com.example.securelink.ui.theme.Gold

// Composable reutilizable para mostrar una estadística individual (número y descripción).
@Composable
fun StatsCard(stat: StatItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stat.count, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Gold)
        Text(text = stat.description, fontSize = 14.sp, color = Color(0xFF94D2BD), textAlign = TextAlign.Center)
    }
}

// Sección que muestra un título y una lista de tarjetas de estadísticas.
@Composable
fun StatsSection(stats: List<StatItem>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "El Pulso de la Seguridad Digital", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(top = 80.dp, bottom = 50.dp))
        // Itera sobre la lista de estadísticas para crear cada StatsCard.
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(67.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            stats.forEach { stat ->
                StatsCard(stat = stat)
            }
        }
    }
}

// Composable principal para la pantalla de inicio (para usuarios no logueados).
@Composable
fun HomeScreen(stats: List<StatItem>, navController: NavController, learnItems: List<LearnItem>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 27.dp)
            .verticalScroll(rememberScrollState()), // Permite que la pantalla sea scrollable.
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        // --- Sección de Branding (Logo y Eslogan) ---
        Image(
            painter = painterResource(id = R.drawable.securelink_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp).padding(bottom = 24.dp)
        )
        Text(
            text = "Tu primera línea de defensa...",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Analizamos enlaces...",
            color = Color(0xFF94D2BD),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )

        // --- Sección de Navegación ---
        // Botón para navegar a la pantalla de registro.
        Button(
            onClick = { navController.navigate("RegistroScreen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Gold)
        ) {
            Text(text = "Crea tu Cuenta Gratis", color = Color.Black)
        }

        // Botón para navegar a la pantalla de inicio de sesión.
        Button(
            onClick = { navController.navigate("Login") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Gold)
        ) {
            Text(text = "Inicia sesion", color = Color.Black);
        }

        // --- Sección de Contenido ---
        StatsSection(stats = stats)

        Spacer(modifier = Modifier.height(32.dp))
    }
}