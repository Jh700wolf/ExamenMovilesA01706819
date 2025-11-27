package com.examena01706819.presentation.screens.puzzle

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.examena01706819.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun PuzzleView(
    dificulty: String,
    // Lab2.4 : Es para pasar funciones de una pantalla a otra.
    onBackClick: () -> Unit,
    viewModel: PuzzleViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    LaunchedEffect(dificulty) {
        viewModel.getPuzzle(dificulty)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sudoku: ${dificulty.uppercase()}") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                uiState.sudoku != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        SudokuBoard(
                            puzzle = uiState.sudoku!!.puzzle,
                            initialPuzzle = uiState.initialPuzzle,
                            selectedCell = selectedCell,
                            onCellClick = { row, col -> selectedCell = row to col },
                        )

                        NumberPad(
                            onNumberClick = { number ->
                                selectedCell?.let { (row, col) ->
                                    viewModel.onCellInput(row, col, number)
                                }
                            },
                            onClearClick = {
                                selectedCell?.let { (row, col) ->
                                    viewModel.onCellInput(row, col, 0)
                                }
                            },
                            modifier = Modifier.padding(bottom = 32.dp),
                        )
                        Button(
                            onClick = {
                                val isSuccess = viewModel.checkSolution()
                                if (isSuccess) {
                                    Toast.makeText(context, "¡Solucion Correcta!", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "Hay errores o está incompleto. Vuelve a Intentarlo", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Revisar")
                        }
                        Button(
                            onClick = { viewModel.resetGame() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("ReIniciar")
                        }
                        Button(
                            onClick = { viewModel.loadNewGame(dificulty) },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Nuevo Juego")
                        }
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuBoard(
    puzzle: List<List<Int?>>,
    initialPuzzle: List<List<Int?>>?,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit,
) {
    val gridSize = puzzle.size
    val boxSize = kotlin.math.sqrt(gridSize.toDouble()).toInt()

    Column(
        modifier =
            Modifier
                .padding(8.dp)
                .border(2.dp, Color.Black),
    ) {
        for (row in 0 until gridSize) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until gridSize) {
                    val value: Int? = puzzle[row][col]

                    val initialVal = initialPuzzle?.getOrNull(row)?.getOrNull(col)
                    val isInitial = initialVal != null && initialVal != 0

                    val isSelected = selectedCell?.first == row && selectedCell.second == col

                    val borderRight = if ((col + 1) % boxSize == 0 && col != gridSize - 1) 2.dp else 0.5.dp
                    val borderBottom = if ((row + 1) % boxSize == 0 && row != gridSize - 1) 2.dp else 0.5.dp

                    Box(
                        modifier =
                            Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .border(width = 0.5.dp, color = Color.Gray)
                                .background(
                                    color =
                                        when {
                                            isSelected -> Color(0xFFBBDEFB)
                                            isInitial -> Color(0xFFE0E0E0)
                                            else -> Color.White
                                        },
                                ).clickable { onCellClick(row, col) },
                        contentAlignment = Alignment.Center,
                    ) {
                        if ((col + 1) % boxSize == 0 && col != gridSize - 1) {
                            Box(
                                Modifier
                                    .align(Alignment.CenterEnd)
                                    .width(2.dp)
                                    .fillMaxSize()
                                    .background(Color.Black),
                            )
                        }
                        if ((row + 1) % boxSize == 0 && row != gridSize - 1) {
                            Box(
                                Modifier
                                    .align(Alignment.BottomCenter)
                                    .height(2.dp)
                                    .fillMaxSize()
                                    .background(Color.Black),
                            )
                        }

                        if (value != null && value != 0) {
                            Text(
                                text = value.toString(),
                                fontSize = if (gridSize > 9) 14.sp else 20.sp,
                                fontWeight = if (isInitial) FontWeight.Bold else FontWeight.Normal,
                                color = if (isInitial) Color.Black else Color(0xFF3F51B5),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun NumberPad(
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            (1..5).forEach { num ->
                NumberButton(number = num, onClick = { onNumberClick(num) }, modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            (6..9).forEach { num ->
                NumberButton(number = num, onClick = { onNumberClick(num) }, modifier = Modifier.weight(1f))
            }
            Button(
                onClick = onClearClick,
                modifier = Modifier.weight(1f),
            ) {
                Text("X")
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun NumberButton(
    number: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = number.toString(), fontSize = 18.sp)
    }
}
