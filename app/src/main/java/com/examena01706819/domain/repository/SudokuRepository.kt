package com.examena01706819.domain.repository

import com.examena01706819.domain.model.Sudoku

interface SudokuRepository {
    suspend fun getSudokuPuzzle(dif: String): Sudoku

    suspend fun clearCache()
}
