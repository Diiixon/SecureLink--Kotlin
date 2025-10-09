package com.example.securelink.view

import com.example.securelink.model.StatItem
import com.example.securelink.ui.theme.SecureLinkTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelink.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.securelink.model.LearnItem

@Composable
fun StatsCard(stat: StatItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stat.count, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEE9B00))
        Text(text = stat.description, fontSize = 14.sp, color = Color(0xFF94D2BD), textAlign = TextAlign.Center)
    }
}

@Composable
fun StatsSection(stats: List<StatItem>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "El Pulso de la Seguridad Digital", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(top = 40.dp, bottom = 16.dp))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            stats.forEach { stat ->
                StatsCard(stat = stat)
            }
        }
    }
}

@Composable
fun HomeScreen(stats: List<StatItem>, learnItems: List<LearnItem>) {
    // Column ahora necesita ser scrollable para que quepa todo el contenido
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(painter = painterResource(id = R.drawable.securelink_logo), contentDescription = "Logo", modifier = Modifier.size(120.dp).padding(bottom = 24.dp))
        Text(text = "Tu primera línea de defensa...", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text(text = "Analizamos enlaces...", color = Color(0xFF94D2BD), fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 16.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().padding(top = 24.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE9B00))) {
            Text(text = "Crea tu Cuenta Gratis", color = Color.Black)
        }

        StatsSection(stats = stats)

        LearnSection(learnItems = learnItems)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LearnCard(item: LearnItem, modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFF005F73), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = item.imageResId),
            contentDescription = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = item.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.description,
            fontSize = 14.sp,
            color = Color(0xFF94D2BD)
        )
    }
}

@Composable
fun LearnSection(learnItems: List<LearnItem>) {
    Column(
        modifier = Modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Conviértete en un Experto",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            learnItems.forEach { item ->
                LearnCard(item = item)
            }
        }
    }
}
