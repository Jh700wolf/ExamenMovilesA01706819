package com.examena01706819.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.examena01706819.presentation.screens.home.components.HomeOptions

val PrimaryColor = Color(0xFF4D88FF)
val BackgroundTop = Color(0xFFF5F7FA)
val BackgroundBottom = Color(0xFFC3CFE2)
val EasyColor = Color(0xFF4CAF50)
val MediumColor = Color(0xFFFF9800)
val HardColor = Color(0xFFF44336)

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel(),
    onPuzzleChosen: (String) -> Unit,
) {
    // mutableStateOf avisa a compose cuando cambia su valor
    // el remember recuerda las actualizaciones de la pantalla
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Sudoku",
                        color = MediumColor,
                    )
                },
            )
        },
        containerColor = BackgroundTop,
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            HomeOptions(
                isLoading = uiState.isLoading,
                error = uiState.error,
                onPuzzleChosen = onPuzzleChosen,
            )
        }
    }
}
