package com.example.mathformula.di

import android.content.Context
import com.example.mathformula.data.dao.CategoryDao
import com.example.mathformula.data.dao.FormulaDao
import com.example.mathformula.data.db.FormulaDatabase
import com.example.mathformula.data.repository.CategoryRepository
import com.example.mathformula.data.repository.FormulaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FormulaDatabase =
        FormulaDatabase.getInstance(context)

    @Provides
    fun provideCategoryDao(db: FormulaDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideFormulaDao(db: FormulaDatabase): FormulaDao = db.formulaDao()

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository =
        CategoryRepository(categoryDao)

    @Provides
    @Singleton
    fun provideFormulaRepository(formulaDao: FormulaDao): FormulaRepository =
        FormulaRepository(formulaDao)
}