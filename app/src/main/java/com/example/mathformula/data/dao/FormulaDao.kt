package com.example.mathformula.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mathformula.data.entity.FormulaEntity
import com.example.mathformula.data.entity.FormulaWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface FormulaDao {
    @Query("SELECT * FROM formulas ORDER BY title ASC")
    fun getAllFormulas(): Flow<List<FormulaEntity>>

    @Transaction
    @Query("SELECT * FROM formulas WHERE categoryId = :categoryId ORDER BY title ASC")
    fun getFormulasByCategory(categoryId: Long): Flow<List<FormulaWithCategory>>

    @Transaction
    @Query("SELECT * FROM formulas WHERE id = :id")
    fun getFormula(id: Long): Flow<FormulaWithCategory>

    @Transaction
    @Query("SELECT * FROM formulas WHERE title LIKE :query OR explanation LIKE :query OR tips LIKE :query ORDER BY title ASC")
    fun searchFormulas(query: String): Flow<List<FormulaWithCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(formulas: List<FormulaEntity>)
}