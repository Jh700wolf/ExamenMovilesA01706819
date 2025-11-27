package com.examena01706819.data.local.model

import com.examena01706819.domain.model.Sudoku

data class SudokuCache(
    val sudoku: Sudoku,
    val lastUpdate: Long,
)
