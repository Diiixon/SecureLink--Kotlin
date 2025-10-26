package com.example.securelink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.LearnItem
import com.example.securelink.repository.LearnRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel para la pantalla "Aprende".
class LearnViewModel: ViewModel() {

    // Instancia el repositorio que proporciona los datos de los recursos de aprendizaje.
    private val repository = LearnRepository()

    // Expone la lista de recursos para que la UI la observe de forma reactiva.
    private val _learnItems = MutableStateFlow<List<LearnItem>>(emptyList())
    val learnItems: StateFlow<List<LearnItem>> = _learnItems.asStateFlow()

    init {
        // Carga los datos al iniciar el ViewModel.
        loadLearnItems()
    }

    // Obtiene los recursos de aprendizaje desde el repositorio.
    private fun loadLearnItems() {
        viewModelScope.launch {
            // Se suscribe al Flow del repositorio y actualiza el estado con la lista recibida.
            repository.getLearnItems().collect { itemList ->
                _learnItems.value = itemList
            }
        }
    }
}