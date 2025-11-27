package com.examena01706819.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.examena01706819.data.local.model.SudokuCache
import com.examena01706819.domain.model.Sudoku
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuPreferences
    @Inject
    constructor(
        @ApplicationContext context: Context,
        private val gson: Gson,
    ) {
        private val prefs: SharedPreferences =
            context.getSharedPreferences(
                PreferencesConstants.PREF_NAME,
                Context.MODE_PRIVATE,
            )

        fun saveSudoku(sudoku: Sudoku) {
            prefs
                .edit()
                .putString(PreferencesConstants.KEY_SUDOKU_CACHE, gson.toJson(sudoku))
                .putLong(PreferencesConstants.KEY_LAST_UPDATE, System.currentTimeMillis())
                .apply()
        }

        fun getSudokuCache(): SudokuCache? {
            val json = prefs.getString(PreferencesConstants.KEY_SUDOKU_CACHE, null)
            val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0)

            if (json == null) return null

            val type = object : TypeToken<Sudoku>() {}.type
            val sudoku: Sudoku = gson.fromJson(json, type)

            return SudokuCache(
                sudoku = sudoku,
                lastUpdate = lastUpdate,
            )
        }

        fun isCacheValid(): Boolean {
            val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0)
            return System.currentTimeMillis() - lastUpdate < PreferencesConstants.CACHE_DURATION
        }

        fun clearCache() {
            prefs.edit().clear().apply()
        }
    }
