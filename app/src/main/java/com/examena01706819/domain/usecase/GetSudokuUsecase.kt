package com.examena01706819.domain.usecase

import com.examena01706819.domain.common.Result
import com.examena01706819.domain.model.Sudoku
import com.examena01706819.domain.repository.SudokuRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSudokuUsecase
    @Inject
    constructor(
        // Inyectado por Hilt
        private val repository: SudokuRepository,
    ) {
        // Puede ser llamado como useCase()
        operator fun invoke(dif: String): Flow<Result<Sudoku>> =
            flow {
                try {
                    emit(Result.Loading)

                    val sudokuPuzzle = repository.getSudokuPuzzle(dif)

                    emit(Result.Success(sudokuPuzzle))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
