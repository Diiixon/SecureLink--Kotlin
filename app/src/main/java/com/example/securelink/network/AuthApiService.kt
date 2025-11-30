package com.example.securelink.network

import com.example.securelink.model.LoginResponse
import com.example.securelink.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz dedicada exclusivamente a los endpoints del microservicio de Autenticación.
 */
interface AuthApiService {

    /**
     * Inicia sesión de un usuario.
     *
     * @param credentials Las credenciales del usuario (correo electrónico y contraseña).
     * @return Una respuesta de inicio de sesión.
     */
    @POST("api/auth/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<LoginResponse>

    /**
     * Registra un nuevo usuario.
     *
     * @param userData Los datos del usuario (nombre, correo electrónico y contraseña).
     * @return Una respuesta de registro.
     */
    @POST("api/auth/register")
    suspend fun register(
        @Body userData: Map<String, String>
    ): Response<RegisterResponse>
}