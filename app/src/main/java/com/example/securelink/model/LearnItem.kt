package com.example.securelink.model

import androidx.annotation.DrawableRes

// Modelo de datos que define la estructura de un recurso de aprendizaje.
// Se usa para mostrar las tarjetas en la sección "Aprende".
data class LearnItem(

    // El ID de un recurso de imagen local (guardado en la carpeta 'drawable').
    @DrawableRes
    val imageResId: Int,

    val title: String,
    
    val description: String
)