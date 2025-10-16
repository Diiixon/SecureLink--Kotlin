package com.example.securelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.securelink.ui.theme.SecureLinkTheme
import com.example.securelink.view.HomeScreen
import com.example.securelink.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureLinkTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF001219)) {

                    val stats by homeViewModel.stats.collectAsStateWithLifecycle()
                    val learnItems by homeViewModel.learnItems.collectAsStateWithLifecycle()

                    HomeScreen(stats = stats)
                }
            }
        }
    }
}
