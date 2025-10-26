package com.example.securelink.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelink.model.LearnItem
import com.example.securelink.ui.theme.DarkTeal

// Composable reutilizable que muestra la tarjeta de un recurso de aprendizaje.
@Composable
fun AprendeCard(learnItem: LearnItem, onClick: () -> Unit) { // El parámetro onClick no se usa actualmente.
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkTeal)
    ) {
        Column {
            // Carga la imagen del recurso desde los drawables locales.
            Image(
                painter = painterResource(id = learnItem.imageResId),
                contentDescription = learnItem.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop // Escala la imagen para que llene el espacio sin deformarse.
            )

            // Sección de texto con el título y la descripción del recurso.
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = learnItem.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = learnItem.description,
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    maxLines = 3
                )
            }
        }
    }
}
