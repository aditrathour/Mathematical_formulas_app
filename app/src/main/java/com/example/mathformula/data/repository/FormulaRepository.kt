package com.example.mathformula.data.repository

import com.example.mathformula.data.dao.FormulaDao
import com.example.mathformula.data.entity.FormulaEntity
import com.example.mathformula.data.entity.FormulaWithCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FormulaRepository @Inject constructor(
    private val formulaDao: FormulaDao
) {
    fun getAllFormulas(): Flow<List<FormulaEntity>> = formulaDao.getAllFormulas()

    fun getFormulasByCategory(categoryId: Long): Flow<List<FormulaWithCategory>> =
        formulaDao.getFormulasByCategory(categoryId)

    fun getFormula(id: Long): Flow<FormulaWithCategory> = formulaDao.getFormula(id)

    fun searchFormulas(query: String): Flow<List<FormulaWithCategory>> =
        formulaDao.searchFormulas("%$query%")

    suspend fun insertAll(formulas: List<FormulaEntity>) = formulaDao.insertAll(formulas)
}