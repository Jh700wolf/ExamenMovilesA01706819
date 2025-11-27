package com.examena01706819.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.examena01706819.presentation.theme.ExamenA01706819Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenA01706819Theme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                }
            }
        }
    }
}
