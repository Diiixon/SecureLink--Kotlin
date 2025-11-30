package com.example.securelink.repository

import com.example.securelink.model.LoginResponse
import com.example.securelink.model.RegisterResponse
import com.example.securelink.network.ApiService
import com.example.securelink.network.RetrofitInstance
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var mockApiService: ApiService

    @Before
    fun setup() {
        mockApiService = mockk()
        mockkObject(RetrofitInstance)
        every { RetrofitInstance.registroService } returns mockApiService
        authRepository = AuthRepository()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // ========== Tests para login() ==========

    @Test
    fun `login exitoso debe retornar success con LoginResponse`() = runTest {
        val loginResponse = LoginResponse(token = "valid_token_12345")
        val response = Response.success(loginResponse)

        coEvery {
            mockApiService.login(any())
        } returns response

        val result = authRepository.login("test@example.com", "password123")

        assertTrue(result.isSuccess)
        assertEquals(loginResponse, result.getOrNull())
    }

    @Test
    fun `login con credenciales invalidas debe retornar failure`() = runTest {
        val errorBody = "Credenciales inválidas".toResponseBody()
        val response = Response.error<LoginResponse>(401, errorBody)

        coEvery {
            mockApiService.login(any())
        } returns response

        val result = authRepository.login("wrong@test.com", "wrongpass")

        assertTrue(result.isFailure)
        assertNotNull(result.exceptionOrNull())
    }

    @Test
    fun `login con body null debe retornar failure`() = runTest {
        val response = Response.success<LoginResponse>(null)

        coEvery {
            mockApiService.login(any())
        } returns response

        val result = authRepository.login("test@test.com", "pass")

        assertTrue(result.isFailure)
    }

    @Test
    fun `login con excepcion de red debe retornar failure`() = runTest {
        val networkException = Exception("Network error")

        coEvery {
            mockApiService.login(any())
        } throws networkException

        val result = authRepository.login("test@test.com", "pass")

        assertTrue(result.isFailure)
        assertEquals(networkException, result.exceptionOrNull())
    }

    @Test
    fun `login debe enviar email y password correctos`() = runTest {
        val loginResponse = LoginResponse(token = "test_token")
        val response = Response.success(loginResponse)

        coEvery {
            mockApiService.login(any())
        } returns response

        authRepository.login("myemail@test.com", "mypassword")

        coVerify {
            mockApiService.login(
                match { request ->
                    request["email"] == "myemail@test.com" &&
                    request["password"] == "mypassword"
                }
            )
        }
    }

    // ========== Tests para register() ==========

    @Test
    fun `register exitoso debe retornar success con RegisterResponse`() = runTest {
        val registerResponse = RegisterResponse(
            token = "new_user_token",
            userId = 1,
            username = "Juan"
        )
        val response = Response.success(registerResponse)

        coEvery {
            mockApiService.register(any())
        } returns response

        val result = authRepository.register("Juan", "juan@test.com", "pass123")

        assertTrue(result.isSuccess)
        assertEquals(registerResponse, result.getOrNull())
    }

    @Test
    fun `register con correo duplicado debe retornar failure con mensaje especifico`() = runTest {
        val errorBody = "Email already exists".toResponseBody()
        val response = Response.error<RegisterResponse>(409, errorBody)

        coEvery {
            mockApiService.register(any())
        } returns response

        val result = authRepository.register("User", "duplicate@test.com", "pass")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("correo ya está registrado") == true)
    }

    @Test
    fun `register con datos invalidos debe retornar failure`() = runTest {
        val errorBody = "Invalid data".toResponseBody()
        val response = Response.error<RegisterResponse>(400, errorBody)

        coEvery {
            mockApiService.register(any())
        } returns response

        val result = authRepository.register("", "invalid", "")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Datos inválidos") == true)
    }

    @Test
    fun `register con error desconocido debe retornar failure con mensaje generico`() = runTest {
        val errorBody = "Unknown error".toResponseBody()
        val response = Response.error<RegisterResponse>(500, errorBody)

        coEvery {
            mockApiService.register(any())
        } returns response

        val result = authRepository.register("User", "test@test.com", "pass")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Error al registrar") == true)
    }

    @Test
    fun `register con body null debe retornar failure`() = runTest {
        val response = Response.success<RegisterResponse>(null)

        coEvery {
            mockApiService.register(any())
        } returns response

        val result = authRepository.register("User", "test@test.com", "pass")

        assertTrue(result.isFailure)
    }

    @Test
    fun `register con excepcion de red debe retornar failure`() = runTest {
        val networkException = Exception("Connection timeout")

        coEvery {
            mockApiService.register(any())
        } throws networkException

        val result = authRepository.register("User", "test@test.com", "pass")

        assertTrue(result.isFailure)
        assertEquals(networkException, result.exceptionOrNull())
    }

    @Test
    fun `register debe enviar nombre, email y password correctos`() = runTest {
        val registerResponse = RegisterResponse(
            token = "token123",
            userId = 2,
            username = "Pedro López"
        )
        val response = Response.success(registerResponse)

        coEvery {
            mockApiService.register(any())
        } returns response

        authRepository.register("Pedro López", "pedro@test.com", "securepass")

        coVerify {
            mockApiService.register(
                match { request ->
                    request["username"] == "Pedro López" &&
                    request["email"] == "pedro@test.com" &&
                    request["password"] == "securepass"
                }
            )
        }
    }
}
