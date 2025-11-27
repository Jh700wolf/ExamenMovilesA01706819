package com.examena01706819.domain.usecase

import com.examena01706819.domain.common.Result
import com.examena01706819.domain.model.Sudoku
import com.examena01706819.domain.repository.SudokuRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewSudokuGame
    @Inject
    constructor(
        // Inyectado por Hilt
        private val repository: SudokuRepository,
    ) {
        // Puede ser llamado como useCase()
        suspend operator fun invoke() {
            repository.clearCache()
        }
    }
