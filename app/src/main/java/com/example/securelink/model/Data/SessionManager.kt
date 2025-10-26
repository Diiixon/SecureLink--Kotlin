package com.example.securelink.model.Data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crea una instancia de DataStore a nivel de aplicación para guardar las preferencias.
// El archivo físico donde se guardarán los datos se llamará "sesion".
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sesion")

// Clase que gestiona la sesión del usuario (inicio y cierre) usando DataStore.
class SessionManager(private val context: Context) {

    companion object {
        // Define la clave (key) para acceder al ID del usuario guardado en DataStore.
        val USER_ID_KEY = intPreferencesKey("user_id")
    }

    // Guarda un ID de usuario en DataStore para mantener la sesión activa.
    suspend fun guardarIdUsuario(id: Int) {
        context.dataStore.edit {
            it[USER_ID_KEY] = id
        }
    }

    // Expone el ID del usuario como un Flow para una observación reactiva.
    // Emite el ID guardado o null si no hay ninguna sesión iniciada.
    val idUsuarioFlow: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY]
        }
}