package com.example.securelink.repository

import com.example.securelink.model.LoginResponse
import com.example.securelink.model.RegisterResponse
import com.example.securelink.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {

    private val apiService = RetrofitInstance.registroService

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(
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

    suspend fun register(
        nombre: String,
        email: String,
        password: String
    ): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(
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
