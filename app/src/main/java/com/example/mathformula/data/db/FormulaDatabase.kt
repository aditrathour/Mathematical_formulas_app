package com.example.mathformula.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mathformula.data.dao.CategoryDao
import com.example.mathformula.data.dao.FormulaDao
import com.example.mathformula.data.entity.CategoryEntity
import com.example.mathformula.data.entity.FormulaEntity
import com.example.mathformula.data.entity.FormulaFts

@Database(
    entities = [CategoryEntity::class, FormulaEntity::class, FormulaFts::class],
    version = 1,
    exportSchema = true
)
abstract class FormulaDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun formulaDao(): FormulaDao

    companion object {
        @Volatile
        private var INSTANCE: FormulaDatabase? = null

        fun getInstance(context: Context): FormulaDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): FormulaDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                FormulaDatabase::class.java,
                "formula.db"
            )
                // TODO: replace with migration strategies when schema changes
                .fallbackToDestructiveMigration()
                // Allow shipping a pre-populated database located in assets/formula.db (optional)
                .createFromAsset("formula.db")
                .build()
    }
}