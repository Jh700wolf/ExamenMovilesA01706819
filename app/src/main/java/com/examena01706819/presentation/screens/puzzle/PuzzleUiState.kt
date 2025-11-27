package com.examena01706819.presentation.screens.puzzle

import com.examena01706819.domain.model.Sudoku

data class PuzzleUiState(
    val sudoku: Sudoku? = null,
    val initialPuzzle: List<List<Int>>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
