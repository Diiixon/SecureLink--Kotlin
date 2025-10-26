package com.example.securelink.model.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

// Define la tabla 'usuario' en la base de datos. Cada instancia de esta clase es una fila en la tabla.
@Entity(tableName = "usuario")
data class Usuario(

    // Define el 'id' como la clave primaria. Se autogenera para asegurar que cada usuario sea único.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Mapea la propiedad 'nombreUsuario' a la columna 'nombre_usuario' en la base de datos.
    @ColumnInfo(name = "nombre_usuario")
    val nombreUsuario: String,

    @ColumnInfo(name = "correo_electronico")
    val correoElectronico: String,

    val contrasena: String,

    @ColumnInfo(name = "fecha_registro")
    val fechaRegistro: LocalDate
)