package com.example.securelink.model.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters // <-- Importar

@Database(entities = [Usuario::class], version = 2) // <-- CAMBIAR A version = 2
@TypeConverters(DateConverter::class) // <-- Añadir esto
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        // 'Volatile' asegura que el valor de INSTANCE esté siempre actualizado
        @Volatile
        private var Instancia: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Retorna la instancia si ya existe
            // Si no, crea la base de datos en un bloque 'synchronized'
            return Instancia ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "securelink_database" // Nombre del archivo de la BD
                )
                    // Añade esto para manejar la migración de v1 a v2 (borra datos antiguos)
                    .fallbackToDestructiveMigration()
                    .build()
                Instancia = instance
                instance
            }
        }
    }
}
