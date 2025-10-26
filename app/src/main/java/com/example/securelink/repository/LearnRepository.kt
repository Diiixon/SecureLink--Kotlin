package com.example.securelink.repository

import com.example.securelink.R
import com.example.securelink.model.LearnItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// Repositorio para obtener los recursos de la sección "Aprende".
class LearnRepository {

    // En este caso, los datos son estáticos y se definen directamente en el código.
    private val learnItems = listOf(
        LearnItem(
            imageResId = R.drawable.malicious_emails,
            title = "Cómo Detectar Phishing",
            description = "Aprende a identificar correos y mensajes fraudulentos que intentan robar tu información."
        ),
        LearnItem(
            imageResId = R.drawable.contrasenas,
            title = "Contraseñas Seguras",
            description = "Descubre cómo crear y gestionar contraseñas robustas para proteger tus cuentas."
        ),
        LearnItem(
            imageResId = R.drawable.wifi,
            title = "Navegación en Redes Wi-Fi Públicas",
            description = "Consejos para mantener tu información segura cuando te conectas a redes abiertas."
        ),
        LearnItem(
            imageResId = R.drawable.software,
            title = "Software y Actualizaciones",
            description = "La importancia de mantener tu software actualizado para protegerte de vulnerabilidades."
        ),
        LearnItem(
            imageResId = R.drawable.anatomia_url, // CORREGIDO: de 'imageRes-Id' a 'imageResId'
            title = "Anatomía de una URL",
            description = "Desglosamos las partes de un enlace para que aprendas a detectar URLs maliciosas a simple vista."
        )
    )

    // Expone la lista de recursos de aprendizaje como un Flow.
    fun getLearnItems(): Flow<List<LearnItem>> {
        return flowOf(learnItems)
    }
}