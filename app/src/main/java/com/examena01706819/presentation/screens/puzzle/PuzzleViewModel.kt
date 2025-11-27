package com.examena01706819.presentation.screens.puzzle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examena01706819.domain.common.Result
import com.examena01706819.domain.usecase.GetNewSudokuGame
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
        private val getNewSudokuGame: GetNewSudokuGame,
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
                                val initialGrid =
                                    result.data.puzzle.map { row ->
                                        row.map { it ?: 0 }
                                    }
                                state.copy(
                                    sudoku = result.data,
                                    initialPuzzle = initialGrid,
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

        fun checkSolution(): Boolean {
            val currentSudoku = _uiState.value.sudoku ?: return false
            val currentBoard = currentSudoku.puzzle
            val solution = currentSudoku.solution

            for (row in currentBoard.indices) {
                for (col in currentBoard[row].indices) {
                    val currentValue = currentBoard[row][col] ?: 0

                    if (currentValue != solution[row][col]) {
                        return false
                    }
                }
            }
            return true
        }

        fun loadNewGame(dif: String) {
            viewModelScope.launch {
                getNewSudokuGame()

                _uiState.update {
                    it.copy(sudoku = null, initialPuzzle = null, isLoading = true)
                }

                getPuzzle(dif)
            }
        }

        fun resetGame() {
            val currentState = _uiState.value
            val initialGrid = currentState.initialPuzzle ?: return
            val currentSudoku = currentState.sudoku ?: return

            val resetBoard =
                initialGrid.map { row ->
                    row.map { value ->
                        if (value == 0) null else value
                    }
                }
            _uiState.update {
                it.copy(
                    sudoku = currentSudoku.copy(puzzle = resetBoard),
                )
            }
        }
    }
