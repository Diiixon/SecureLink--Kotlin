package com.example.securelink.model.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    // Usamos 'suspend' para que se llame desde una corutina (fuera del hilo principal)
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarUsuario(usuario: Usuario)

    // Ejemplo de otra consulta que podrías necesitar
    @Query("SELECT * FROM usuario WHERE correo_electronico = :correo")
    suspend fun getUsuarioPorCorreo(correo: String): Usuario?

    @Query("SELECT * FROM usuario WHERE id = :id LIMIT 1")
    suspend fun getUsuarioPorId(id: Int): Usuario?
}