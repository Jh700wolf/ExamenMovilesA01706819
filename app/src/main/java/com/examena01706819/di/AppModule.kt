package com.examena01706819.di

import com.examena01706819.data.local.preferences.SudokuPreferences
import com.examena01706819.data.remote.api.SudokuApi
import com.examena01706819.data.repository.SudokuRepositoryImpl
import com.examena01706819.domain.repository.SudokuRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSudokuApi(retrofit: Retrofit): SudokuApi = retrofit.create(SudokuApi::class.java)

    @Provides
    @Singleton
    fun provideSudokuRepository(
        api: SudokuApi,
        preferences: SudokuPreferences,
    ): SudokuRepository = SudokuRepositoryImpl(api, preferences)
}
