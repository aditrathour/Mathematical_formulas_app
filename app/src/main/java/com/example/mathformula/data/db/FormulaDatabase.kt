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

                        // Prepopulate formulas (10 each for Algebra, Geometry, Trigonometry, Calculus)

                        // Algebra (categoryId = 1)
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (1, 1, 'Quadratic Formula', 'x = \\frac{-b \\pm \\sqrt{b^2 - 4ac}}{2a}', 'Solves ax^2 + bx + c = 0', 'Compute discriminant first.')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (2, 1, 'Sum of Arithmetic Series', 'S_n = \\frac{n}{2}(a_1 + a_n)', 'Sum of first n terms in an arithmetic progression', 'Average first and last term times n.')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (3, 1, 'Binomial Theorem', '(a + b)^n = \\sum_{k=0}^{n} {n\\choose k} a^{n-k} b^{k}', 'Expands powers of binomials', 'Pascal''s triangle provides coefficients.')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (4, 1, 'Difference of Squares', 'a^2 - b^2 = (a-b)(a+b)', 'Factorization identity', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (5, 1, 'Sum of First n Natural Numbers', '1+2+\\dots+n = \\frac{n(n+1)}{2}', 'Arithmetic progression sum', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (6, 1, 'Sum of First n Squares', '1^2+2^2+\\dots+n^2 = \\frac{n(n+1)(2n+1)}{6}', 'Useful in series computations', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (7, 1, 'Sum of First n Cubes', '1^3+2^3+\\dots+n^3 = (\\frac{n(n+1)}{2})^2', 'Cube sum identity', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (8, 1, 'Geometric Series', 'S_n = a\\,\\frac{1-r^{n}}{1-r}', 'Sum of first n terms of GP', 'For |r|<1, as n→∞, S = a/(1-r)')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (9, 1, 'Change of Base (Log)', '\\log_b a = \\frac{\\log_k a}{\\log_k b}', 'Convert logarithm base', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (10, 1, 'Factor Theorem', 'If\\; f(c)=0 \\Rightarrow (x-c) \\text{ is factor of } f(x)', 'Polynomial factorization', '')")

                        // Geometry (categoryId = 2)
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (11, 2, 'Area of Circle', 'A = \\pi r^2', 'Area enclosed by circle', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (12, 2, 'Circumference of Circle', 'C = 2\\pi r', 'Perimeter of circle', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (13, 2, 'Area of Triangle', 'A = \\frac{1}{2} b h', 'Basic triangle area', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (14, 2, 'Heron''s Formula', 'A = \\sqrt{s(s-a)(s-b)(s-c)}', 'Area from three sides', 's = (a+b+c)/2')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (15, 2, 'Volume of Sphere', 'V = \\frac{4}{3} \\pi r^3', '3D volume', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (16, 2, 'Surface Area of Sphere', 'A = 4\\pi r^2', 'Surface area of sphere', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (17, 2, 'Volume of Cylinder', 'V = \\pi r^2 h', 'Cylinder volume', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (18, 2, 'Surface Area of Cone', 'A = \\pi r (r + l)', 'l is slant height', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (19, 2, 'Area of Trapezoid', 'A = \\frac{1}{2} (a+b) h', 'Trapezoid area', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (20, 2, 'Pythagorean Theorem', 'a^2 + b^2 = c^2', 'Right triangle relation', 'Solve for unknown side.')")

                        // Trigonometry (categoryId = 3)
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (21, 3, 'Sine Rule', '\\frac{a}{\\sin A} = \\frac{b}{\\sin B} = \\frac{c}{\\sin C}', 'Relates sides & angles in any triangle', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (22, 3, 'Cosine Rule', 'c^2 = a^2 + b^2 - 2ab \\cos C', 'Generalization of Pythagoras', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (23, 3, 'Fundamental Identity', '\\sin^2 x + \\cos^2 x = 1', 'Basic trig identity', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (24, 3, 'Tan Identity', '\\tan x = \\frac{\\sin x}{\\cos x}', 'Definition of tan', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (25, 3, 'Double Angle (Sine)', '\\sin 2x = 2 \\sin x \\cos x', 'Double angle formula', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (26, 3, 'Double Angle (Cos)', '\\cos 2x = \\cos^2 x - \\sin^2 x', 'Alternative forms exist', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (27, 3, 'Area with Sine', 'A = \\frac{1}{2}ab\\sin C', 'Triangle area via 2 sides & included angle', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (28, 3, 'Radians to Degrees', '1\\text{ rad} = \\frac{180}{\\pi}^\\circ', 'Conversion', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (29, 3, 'Sine of Sum', '\\sin (A+B) = \\sin A \\cos B + \\cos A \\sin B', 'Angle addition formula', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (30, 3, 'Cosine of Difference', '\\cos (A-B) = \\cos A \\cos B + \\sin A \\sin B', 'Angle subtraction formula', '')")

                        // Calculus (categoryId = 4)
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (31, 4, 'Power Rule (Derivative)', '\\frac{d}{dx} x^n = n x^{n-1}', 'Differentiation rule', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (32, 4, 'Integral of Power', '\\int x^n dx = \\frac{x^{n+1}}{n+1} + C', 'n ≠ -1', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (33, 4, 'Derivative of sin', '\\frac{d}{dx} \\sin x = \\cos x', '', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (34, 4, 'Derivative of cos', '\\frac{d}{dx} \\cos x = -\\sin x', '', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (35, 4, 'Integral of sin', '\\int \\sin x \\; dx = -\\cos x + C', '', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (36, 4, 'Integral of cos', '\\int \\cos x \\; dx = \\sin x + C', '', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (37, 4, 'Product Rule', '\\frac{d}{dx}[uv] = u''v + uv''', 'Derivative of product', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (38, 4, 'Quotient Rule', '\\frac{d}{dx}\\left(\\frac{u}{v}\\right) = \\frac{u''v - uv''}{v^2}', 'Derivative of quotient', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (39, 4, 'Chain Rule', '\\frac{d}{dx} f(g(x)) = f''(g(x))\\,g''(x)', 'Composite differentiation', '')")
                        db.execSQL("INSERT INTO formulas(id, categoryId, title, latex, explanation, tips) VALUES (40, 4, 'Definite Integral Area', 'A = \\int_{a}^{b} f(x)\\,dx', 'Area under curve between a and b', '')")
                    }
                })
                .build()
    }
}