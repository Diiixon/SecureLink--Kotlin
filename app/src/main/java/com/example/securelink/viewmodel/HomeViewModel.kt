package com.example.securelink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.LearnItem
import com.example.securelink.repository.LearnRepository
import com.example.securelink.model.StatItem
import com.example.securelink.repository.StatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel para la pantalla de inicio (HomeScreen), para usuarios no logueados.
class HomeViewModel : ViewModel() {
    // Instancia los repositorios que proporcionan los datos necesarios para esta pantalla.
    private val statsRepository = StatsRepository()
    private val learnRepository = LearnRepository()

    // Expone el estado de las estadísticas para que la UI lo observe.
    private val _stats = MutableStateFlow<List<StatItem>>(emptyList())
    val stats: StateFlow<List<StatItem>> = _stats.asStateFlow()

    // Expone el estado de los recursos de aprendizaje para que la UI lo observe.
    private val _learnItems = MutableStateFlow<List<LearnItem>>(emptyList())
    val learnItems: StateFlow<List<LearnItem>> = _learnItems.asStateFlow()

    // El bloque init se ejecuta al crear el ViewModel, disparando la carga de datos.
    init {
        loadStats()
        loadLearnItems()
    }

    // Obtiene las estadísticas desde el repositorio y actualiza el estado.
    private fun loadStats() {
        viewModelScope.launch {
            // Se suscribe al Flow del repositorio para recibir los datos.
            statsRepository.getStats().collect { statsList ->
                _stats.value = statsList
            }
        }
    }

    // Obtiene los recursos de aprendizaje desde el repositorio y actualiza el estado.
    private fun loadLearnItems() {
        viewModelScope.launch {
            // Se suscribe al Flow del repositorio para recibir los datos.
            learnRepository.getLearnItems().collect { learnList ->
                _learnItems.value = learnList
            }
        }
    }
}