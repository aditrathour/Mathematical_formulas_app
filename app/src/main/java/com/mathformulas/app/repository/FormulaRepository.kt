package com.mathformulas.app.repository

import com.mathformulas.app.data.Formula
import com.mathformulas.app.data.FormulaDao
import kotlinx.coroutines.flow.Flow

class FormulaRepository(private val formulaDao: FormulaDao) {
    
    fun getAllFormulas(): Flow<List<Formula>> = formulaDao.getAllFormulas()
    
    fun getFormulasByCategory(category: String): Flow<List<Formula>> = 
        formulaDao.getFormulasByCategory(category)
    
    fun getFormulasBySubcategory(subcategory: String): Flow<List<Formula>> = 
        formulaDao.getFormulasBySubcategory(subcategory)
    
    fun getFavoriteFormulas(): Flow<List<Formula>> = formulaDao.getFavoriteFormulas()
    
    fun getFormulasByDifficulty(difficulty: String): Flow<List<Formula>> = 
        formulaDao.getFormulasByDifficulty(difficulty)
    
    suspend fun getFormulaById(id: Int): Formula? = formulaDao.getFormulaById(id)
    
    fun searchFormulas(query: String): Flow<List<Formula>> = formulaDao.searchFormulas(query)
    
    suspend fun getAllCategories(): List<String> = formulaDao.getAllCategories()
    
    suspend fun getSubcategoriesByCategory(category: String): List<String> = 
        formulaDao.getSubcategoriesByCategory(category)
    
    suspend fun insertFormula(formula: Formula) = formulaDao.insertFormula(formula)
    
    suspend fun updateFormula(formula: Formula) = formulaDao.updateFormula(formula)
    
    suspend fun deleteFormula(formula: Formula) = formulaDao.deleteFormula(formula)
    
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) = 
        formulaDao.updateFavoriteStatus(id, isFavorite)
    
    suspend fun getFormulaCount(): Int = formulaDao.getFormulaCount()
    
    suspend fun getRelatedFormulas(formulaId: Int): List<Formula> = 
        formulaDao.getRelatedFormulas(formulaId)
}