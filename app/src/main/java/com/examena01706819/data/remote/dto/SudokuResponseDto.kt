package com.examena01706819.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SudokuResponseDto(
    @SerializedName("puzzle") val puzzle: List<List<Int>>,
    @SerializedName("solution") val solution: List<List<Int>>,
)
