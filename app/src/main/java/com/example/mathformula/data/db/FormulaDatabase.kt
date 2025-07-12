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
import androidx.room.Callback
import androidx.sqlite.db.SupportSQLiteDatabase

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
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // Prepopulate categories
                        db.execSQL("INSERT INTO categories(id, name) VALUES (1, 'Algebra')")
                        db.execSQL("INSERT INTO categories(id, name) VALUES (2, 'Geometry')")
                        db.execSQL("INSERT INTO categories(id, name) VALUES (3, 'Trigonometry')")

                        // Prepopulate formulas
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (1, 1, 'Quadratic Formula', 'x = \\frac{-b \\pm \\sqrt{b^2 - 4ac}}{2a}', 'Solution of ax^2 + bx + c = 0', 'Remember to calculate discriminant first.')")

                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (2, 2, 'Pythagorean Theorem', 'a^2 + b^2 = c^2', 'Relation between the sides of a right triangle', 'Great for distance calculations.')")

                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (3, 3, 'Sine Rule', '\\frac{a}{\\sin A} = \\frac{b}{\\sin B} = \\frac{c}{\\sin C}', 'Relates sides and angles in any triangle', 'Use to find unknown side or angle.')")
                    }
                })
                .build()
    }
}