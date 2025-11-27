package com.examena01706819.data.mapper

import com.examena01706819.data.remote.dto.SudokuResponseDto
import com.examena01706819.domain.model.Sudoku

fun SudokuResponseDto.toDomain(difficultySelected: String): Sudoku =
    Sudoku(
        puzzle = puzzle,
        solution = solution,
        difficulty = difficultySelected,
    )
