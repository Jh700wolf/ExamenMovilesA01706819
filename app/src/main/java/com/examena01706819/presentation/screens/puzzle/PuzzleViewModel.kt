package com.examena01706819.presentation.screens.puzzle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examena01706819.domain.common.Result
import com.examena01706819.domain.usecase.GetSudokuUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel
    @Inject
    constructor(
        private val getSudokuUsecase: GetSudokuUsecase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(PuzzleUiState())
        val uiState: StateFlow<PuzzleUiState> = _uiState.asStateFlow()

        fun getPuzzle(dif: String) {
            viewModelScope.launch {
                getSudokuUsecase(dif = dif).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )
                            is Result.Success -> {
                                val initialGrid = result.data.puzzle.map { it.toList() }
                                state.copy(
                                    sudoku = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            }

                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message,
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }

        fun onCellInput(
            row: Int,
            col: Int,
            value: Int,
        ) {
            val currentSudoku = _uiState.value.sudoku ?: return
            val initialGrid = _uiState.value.initialPuzzle ?: return

            if (initialGrid[row][col] != 0) return

            val newGrid = currentSudoku.puzzle.map { it.toMutableList() }.toMutableList()
            newGrid[row][col] = value

            _uiState.update {
                it.copy(sudoku = currentSudoku.copy(puzzle = newGrid))
            }
        }
    }
