package com.example.securelink.model

import androidx.annotation.DrawableRes

data class LearnItem(
    @DrawableRes val imageResId: Int,
    val title: String,
    val description: String
)