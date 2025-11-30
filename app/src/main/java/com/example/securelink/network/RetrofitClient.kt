package com.example.securelink.network

import com.example.securelink.repository.StatsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto responsable de crear y configurar instancias de Retrofit para los diferentes microservicios.
 */
object RetrofitClient {
    // URLs para los tres microservicios
    private const val AUTH_BASE_URL = "http://54.147.108.252:8080/"      // Servicio de Autenticación
    private const val ANALYSIS_BASE_URL = "http://54.144.201.173:8081/" // Servicio de Análisis
    private const val STATS_BASE_URL = "http://98.95.213.83:8082/"       // Servicio de Estadísticas

    // Interceptor para registrar solicitudes y respuestas HTTP.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient con interceptor de registro y tiempos de espera.
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // --- Instancias de Retrofit para cada microservicio ---
    private val authRetrofit = Retrofit.Builder().baseUrl(AUTH_BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()
    private val analysisRetrofit = Retrofit.Builder().baseUrl(ANALYSIS_BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()
    private val statsRetrofit = Retrofit.Builder().baseUrl(STATS_BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()

    // --- Instancias de Servicios de API ---

    // Servicio para Autenticación
    val authApiService: AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }

    // Servicio para Análisis
    val analysisApiService: ApiService by lazy {
        analysisRetrofit.create(ApiService::class.java)
    }

    // Servicio para Estadísticas
    val statsApiService: StatsApiService by lazy {
        statsRetrofit.create(StatsApiService::class.java)
    }

    // --- Repositorios (si se centralizan aquí) ---
    val statsRepository: StatsRepository by lazy {
        StatsRepository(statsApiService)
    }
}