package com.example.securelink.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.securelink.viewmodel.LearnViewModel

// Composable principal de la pantalla "Aprende".
@Composable
fun LearnScreen(viewModel: LearnViewModel = viewModel()) {
    // Obtiene la lista de recursos de aprendizaje desde el ViewModel y se suscribe a sus cambios.
    val learnItems by viewModel.learnItems.collectAsState()

    // LazyColumn es la forma eficiente de mostrar una lista vertical.
    // Solo compone y dibuja los elementos que son visibles en la pantalla.
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp), // Añade padding alrededor de la lista.
        verticalArrangement = Arrangement.spacedBy(16.dp) // Añade espacio entre cada tarjeta.
    ) {
        // Itera sobre la lista de 'learnItems' y crea una 'AprendeCard' para cada uno.
        items(learnItems) { learnItem ->
            AprendeCard(learnItem = learnItem) { /* La acción de clic está desactivada por ahora. */ }
        }
    }
}
