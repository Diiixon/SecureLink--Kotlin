package com.example.securelink.model.Data

import androidx.room.TypeConverter
import java.time.LocalDate

// Conversor para que Room pueda manejar el tipo de dato LocalDate, ya que solo puede guardar tipos simples.
class DateConverter {

    // Convierte un objeto LocalDate a un número (Long) para poder guardarlo en la base de datos.
    @TypeConverter
    fun toTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    // Convierte el número (Long) de la base de datos de vuelta a un objeto LocalDate al leerlo.
    @TypeConverter
    fun toDate(timestamp: Long?): LocalDate? {
        return timestamp?.let { LocalDate.ofEpochDay(it) }
    }
}