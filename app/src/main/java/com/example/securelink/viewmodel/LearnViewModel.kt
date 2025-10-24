package com.example.securelink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelink.model.LearnItem
import com.example.securelink.repository.LearnRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LearnViewModel: ViewModel() {

    private val repository = LearnRepository()

    private val _learnItems = MutableStateFlow<List<LearnItem>>(emptyList())
    val learnItems: StateFlow<List<LearnItem>> = _learnItems.asStateFlow()

    init {
        loadLearnItems()
    }

    private fun loadLearnItems() {
        viewModelScope.launch {
            _learnItems.value = repository.getLearnItems()
        }
    }
}