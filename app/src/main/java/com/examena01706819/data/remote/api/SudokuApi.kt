package com.examena01706819.data.remote.api

import android.R
import com.examena01706819.data.remote.dto.SudokuResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SudokuApi {
    @GET("sudokugenerate")
    suspend fun getSudoku(
        @Header("X-Api-Key") apiKey: String = "4/rU28KnKzCw+psLdqKf+g==a7i4WzhkH7X8lazc",
        @Query("width") widt: Int = 3,
        @Query("height") height: Int = 3,
        @Query("difficulty") difficulty: String = "medium",
    ): SudokuResponseDto
}
