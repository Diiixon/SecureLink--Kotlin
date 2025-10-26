package com.example.securelink.model.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Aquí se define la base de datos.
@Database(entities = [Usuario::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    // Función para acceder a las operaciones de la base de datos (las queries del DAO).
    abstract fun usuarioDao(): UsuarioDao

    // 'companion object' para que solo haya una instancia de la base de datos
    companion object {

        @Volatile
        private var Instancia: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instancia ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "securelink_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Instancia = instance
                instance
            }
        }
    }
}