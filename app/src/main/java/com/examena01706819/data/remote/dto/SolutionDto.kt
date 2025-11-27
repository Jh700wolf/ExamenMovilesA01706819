package com.examena01706819.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SolutionDto(
    @SerializedName("solution") val solution: List<List<Int>>,
)
