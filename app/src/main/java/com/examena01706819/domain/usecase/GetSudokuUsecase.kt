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
                    // Primer valor: Loading
                    emit(Result.Loading)

                    // Obtiene datos
                    val sudokuPuzzle = repository.getSudokuPuzzle(dif)

                    // Segundo valor: Success con datos
                    emit(Result.Success(sudokuPuzzle))
                } catch (e: Exception) {
                    // O Error si algo falla
                    emit(Result.Error(e))
                }
            }
    }
