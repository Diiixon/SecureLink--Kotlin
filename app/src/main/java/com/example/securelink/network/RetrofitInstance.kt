package com.example.securelink.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto responsable de crear instancias de Retrofit.
 */
object RetrofitInstance {

    /**
     * Crea una instancia de Retrofit con la URL base dada.
     *
     * @param baseUrl La URL base de la API.
     * @return Una instancia de Retrofit.
     */
    private fun createRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     * Servicio para el registro.
     */
    val registroService: ApiService by lazy {
        createRetrofit("http://54.147.108.252:8080/").create(ApiService::class.java)
    }

    /**
     * Servicio para el análisis.
     */
    val analisisService: ApiService by lazy {
        createRetrofit("http://54.144.201.173:8081/").create(ApiService::class.java)
    }
}