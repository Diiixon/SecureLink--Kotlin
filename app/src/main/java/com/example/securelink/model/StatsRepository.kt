package com.example.securelink.model

class StatsRepository {
    fun getStats(): List<StatItem>{
        return listOf(
            StatItem(count = "1.2M", description = "Enlaces Analizados"),
            StatItem(count = "87.000+", description = "Amenazas Bloqueadas"),
            StatItem(count = "99.8%", description = "Tasa de Detección")
        )
    }
}