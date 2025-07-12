package com.example.mathformula.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mathformula.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}