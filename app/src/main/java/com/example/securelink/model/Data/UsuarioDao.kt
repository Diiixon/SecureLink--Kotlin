package com.example.securelink.model.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Interfaz para el acceso a datos (DAO) de la tabla de usuarios.
@Dao
interface UsuarioDao {

    // Inserta un nuevo usuario. Si ya existe, aborta la operación.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarUsuario(usuario: Usuario)

    // Busca un usuario por su correo electrónico.
    @Query("SELECT * FROM usuario WHERE correo_electronico = :correo")
    suspend fun getUsuarioPorCorreo(correo: String): Usuario?

    // Obtiene un usuario por su ID y lo emite como un Flow.
    // Flow permite que la UI se actualice automáticamente si los datos de este usuario cambian.
    @Query("SELECT * FROM usuario WHERE id = :id LIMIT 1")
    fun getUsuarioPorId(id: Int): Flow<Usuario?>

    // Actualiza el nombre de un usuario específico a través de su ID.
    @Query("UPDATE usuario SET nombre_usuario = :newName WHERE id = :userId")
    suspend fun updateUsername(userId: Int, newName: String)

    // Elimina un usuario de la base de datos usando su ID.
    @Query("DELETE FROM usuario WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

}