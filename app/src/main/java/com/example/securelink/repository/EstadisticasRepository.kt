package com.example.securelink.repository

import com.example.securelink.model.Distribucion
import com.example.securelink.model.UsuarioComparativaResponse
import com.example.securelink.network.RetrofitInstance
import retrofit2.Response

class EstadisticasRepository {

    private val apiService = RetrofitInstance.estadisticasService

    suspend fun getComparativa(token: String, userId: Long): Response<UsuarioComparativaResponse> {
        return apiService.getUsuarioComparativa(token = "Bearer $token", userId = userId)
    }

    suspend fun getDistribucionUsuario(token: String, userId: Long): Response<List<Distribucion>> {
        return apiService.getUsuarioDistribucion(token = "Bearer $token", userId = userId)
    }
}
