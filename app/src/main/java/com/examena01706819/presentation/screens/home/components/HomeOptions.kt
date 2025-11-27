package com.examena01706819.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val PrimaryColor = Color(0xFF4D88FF)
val BackgroundTop = Color(0xFFF5F7FA)
val BackgroundBottom = Color(0xFFC3CFE2)
val EasyColor = Color(0xFF4CAF50)
val MediumColor = Color(0xFFFF9800)
val HardColor = Color(0xFFF44336)

@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeOptions(
    isLoading: Boolean,
    error: String?,
    onPuzzleChosen: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            error != null -> {
                Text(
                    text = error,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error,
                )
            }
            else -> {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    DifficultyButtons(title = "Facil", color = EasyColor, onClick = { onPuzzleChosen("easy") })
                    Spacer(modifier = Modifier.height(20.dp))
                    DifficultyButtons(title = "Medio", color = MediumColor, onClick = { onPuzzleChosen("medium") })
                    Spacer(modifier = Modifier.height(20.dp))
                    DifficultyButtons(title = "Dificil", color = HardColor, onClick = { onPuzzleChosen("hard") })
                }
            }
        }
    }
}
