package com.examena01706819.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PuzzleDto(
    @SerializedName("puzzle") val puzzle: List<List<Int>>,
)
