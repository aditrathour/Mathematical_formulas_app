package com.example.mathformula.data.repository

import com.example.mathformula.data.dao.CategoryDao
import com.example.mathformula.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
}