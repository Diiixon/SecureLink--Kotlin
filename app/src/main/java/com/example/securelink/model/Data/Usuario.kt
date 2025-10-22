package com.example.securelink.model.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate // <-- Importar

@Entity(tableName = "usuario") // nombre de la tabla
@TypeConverters(DateConverter::class) // <-- Añadir esto
data class Usuario(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // ID autoincremental

    @ColumnInfo(name = "nombre_usuario")
    val nombreUsuario: String,

    @ColumnInfo(name = "correo_electronico")
    val correoElectronico: String,

    val contrasena: String,

    // --- NUEVO CAMPO ---
    @ColumnInfo(name = "fecha_registro")
    val fechaRegistro: LocalDate // <-- Añadir esto
)