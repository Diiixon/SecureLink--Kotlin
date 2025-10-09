package com.example.securelink.model

import com.example.securelink.R

class LearnRepository {

    fun getLearnItems(): List<LearnItem> {
        return listOf(
            LearnItem(R.drawable.malicious_emails, "Detectar un Email Falso", "Aprende a identificar remitentes sospechosos, errores y tácticas de urgencia."),
            LearnItem(R.drawable.anatomia_url, "Anatomia de una URL Maliciosa", "Desglosamos cómo los atacantes ocultan dominios falsos en enlaces."),
            LearnItem(R.drawable.peligros_ocultos, "¿Es seguro ese código QR?", "Te enseñamos qué verificar antes de escanear un código QR."),
        )
    }
}