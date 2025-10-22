package com.example.securelink.model.Data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para crear el DataStore.
// El nombre "sesion" será el nombre del archivo guardado en el teléfono.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sesion")

class SessionManager(private val context: Context) {

    companion object {
        // Creamos la "llave" (key) para guardar el ID del usuario
        val USER_ID_KEY = intPreferencesKey("user_id")
    }

    /**
     * Guarda el ID del usuario en DataStore.
     * Esta es una función 'suspend' porque es asíncrona.
     */
    suspend fun guardarIdUsuario(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
        }
    }

    /**
     * Lee el ID del usuario desde DataStore.
     * Devuelve un 'Flow', lo que permite a la UI "observar"
     * cambios en la sesión en tiempo real.
     */
    val idUsuarioFlow: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            // Devuelve el ID guardado, o 'null' si no hay nada.
            preferences[USER_ID_KEY]
        }
}