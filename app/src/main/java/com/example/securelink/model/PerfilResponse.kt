package com.example.securelink.model

data class PerfilResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: String? = null
)