package com.example.securelink.repository

import com.example.securelink.model.StatItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class StatsRepository {
    // CORREGIDO: Ahora devuelve un Flow para ser consistente y reactivo.
    fun getStats(): Flow<List<StatItem>> {
        val stats = listOf(
            StatItem(count = "1.2M", description = "Enlaces Analizados"),
            StatItem(count = "87.000+", description = "Amenazas Bloqueadas"),
            StatItem(count = "99.8%", description = "Tasa de Detección")
        )
        // Envuelve la lista estática en un Flow.
        return flowOf(stats)
    }
}