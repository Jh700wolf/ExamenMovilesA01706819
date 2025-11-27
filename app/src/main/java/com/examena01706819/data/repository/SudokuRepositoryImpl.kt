package com.examena01706819.data.repository

import com.examena01706819.data.local.preferences.SudokuPreferences
import com.examena01706819.data.mapper.toDomain
import com.examena01706819.data.remote.api.SudokuApi
import com.examena01706819.domain.model.Sudoku
import com.examena01706819.domain.repository.SudokuRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class SudokuRepositoryImpl
    @Inject
    constructor(
        private val api: SudokuApi,
        private val preferences: SudokuPreferences,
    ) : SudokuRepository {
        override suspend fun getSudokuPuzzle(dif: String): Sudoku {
            // Intentar obtener del caché primero
            preferences.getSudokuCache()?.let { cache ->
                if (preferences.isCacheValid()) {
                    return cache.sudoku
                }
            }

            return try {
                // Si no hay caché o expiró, obtener de la API
                val response = api.getSudoku(difficulty = dif)
                val savedSudoku = response.toDomain()
                preferences.saveSudoku(
                    sudoku = savedSudoku,
                )
                savedSudoku
            } catch (e: Exception) {
                // Si hay error, intentar usar caché aunque haya expirado
                preferences.getSudokuCache()?.let { cache ->
                    return cache.sudoku
                } ?: throw e
            }
        }
    }
