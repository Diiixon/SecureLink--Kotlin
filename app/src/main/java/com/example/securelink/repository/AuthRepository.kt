package com.example.securelink.repository

import com.example.securelink.model.LoginResponse
import com.example.securelink.model.RegisterResponse
import com.example.securelink.network.AuthApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio para manejar operaciones de datos relacionadas con la autenticación.
 * CORRECCIÓN: El repositorio ahora recibe el servicio de API a través del constructor.
 *
 * @param authApiService El servicio de API de autenticación.
 */
class AuthRepository(private val authApiService: AuthApiService) {

    /**
     * Inicia sesión de un usuario.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto Result que contiene la respuesta de inicio de sesión o una excepción.
     */
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // CORRECCIÓN: Se usa la nueva AuthApiService
                val response = authApiService.login(
                    mapOf(
                        "email" to email,
                        "password" to password
                    )
                )

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Credenciales inválidas"
                    Result.failure(Exception(errorBody))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param nombre El nombre del usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto Result que contiene la respuesta de registro o una excepción.
     */
    suspend fun register(
        nombre: String,
        email: String,
        password: String
    ): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // CORRECCIÓN: Se usa la nueva AuthApiService
                val response = authApiService.register(
                    mapOf(
                        "username" to nombre,
                        "email" to email,
                        "password" to password
                    )
                )

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorMsg = when (response.code()) {
                        409 -> "El correo ya está registrado"
                        400 -> "Datos inválidos"
                        else -> {
                            val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                            "Error al registrar: $errorBody"
                        }
                    }
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}