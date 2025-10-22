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

class HomeViewModel : ViewModel() {
    private val statsRepository = StatsRepository()
    private val learnRepository = LearnRepository()

    private val _stats = MutableStateFlow<List<StatItem>>(emptyList())
    val stats: StateFlow<List<StatItem>> = _stats.asStateFlow()

    private val _learnItems = MutableStateFlow<List<LearnItem>>(emptyList())
    val learnItems: StateFlow<List<LearnItem>> = _learnItems.asStateFlow()

    init {
        loadStats()
        loadLearnItems()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _stats.value = statsRepository.getStats()
        }
    }
    private fun loadLearnItems() {
        viewModelScope.launch {
            _learnItems.value = learnRepository.getLearnItems()
        }
    }
}