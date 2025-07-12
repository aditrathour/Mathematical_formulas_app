package com.mathformulas.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FormulaDao {
    
    @Query("SELECT * FROM formulas ORDER BY name ASC")
    fun getAllFormulas(): Flow<List<Formula>>
    
    @Query("SELECT * FROM formulas WHERE category = :category ORDER BY name ASC")
    fun getFormulasByCategory(category: String): Flow<List<Formula>>
    
    @Query("SELECT * FROM formulas WHERE subcategory = :subcategory ORDER BY name ASC")
    fun getFormulasBySubcategory(subcategory: String): Flow<List<Formula>>
    
    @Query("SELECT * FROM formulas WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteFormulas(): Flow<List<Formula>>
    
    @Query("SELECT * FROM formulas WHERE difficulty = :difficulty ORDER BY name ASC")
    fun getFormulasByDifficulty(difficulty: String): Flow<List<Formula>>
    
    @Query("SELECT * FROM formulas WHERE id = :id")
    suspend fun getFormulaById(id: Int): Formula?
    
    @Query("""
        SELECT * FROM formulas 
        WHERE name LIKE '%' || :query || '%' 
        OR description LIKE '%' || :query || '%' 
        OR tags LIKE '%' || :query || '%'
        OR concept LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchFormulas(query: String): Flow<List<Formula>>
    
    @Query("SELECT DISTINCT category FROM formulas ORDER BY category ASC")
    suspend fun getAllCategories(): List<String>
    
    @Query("SELECT DISTINCT subcategory FROM formulas WHERE category = :category AND subcategory IS NOT NULL ORDER BY subcategory ASC")
    suspend fun getSubcategoriesByCategory(category: String): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormula(formula: Formula)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormulas(formulas: List<Formula>)
    
    @Update
    suspend fun updateFormula(formula: Formula)
    
    @Delete
    suspend fun deleteFormula(formula: Formula)
    
    @Query("UPDATE formulas SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
    
    @Query("SELECT COUNT(*) FROM formulas")
    suspend fun getFormulaCount(): Int
    
    @Query("SELECT * FROM formulas WHERE relatedFormulas LIKE '%' || :formulaId || '%'")
    suspend fun getRelatedFormulas(formulaId: Int): List<Formula>
}