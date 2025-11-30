package com.example.securelink.model

import androidx.annotation.DrawableRes

/**
 * Modelo de datos que define la estructura de un recurso de aprendizaje.
 * Se usa para mostrar las tarjetas en la sección "Aprende".
 *
 * @property imageResId El ID de un recurso de imagen local (guardado en la carpeta 'drawable').
 * @property title El título del recurso de aprendizaje.
 * @property description La descripción del recurso de aprendizaje.
 */
data class LearnItem(

    // El ID de un recurso de imagen local (guardado en la carpeta 'drawable').
    @DrawableRes
    val imageResId: Int,

    val title: String,

    val description: String
)