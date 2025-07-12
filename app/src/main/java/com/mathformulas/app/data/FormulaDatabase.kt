package com.mathformulas.app.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Formula::class],
    version = 1,
    exportSchema = false
)
abstract class FormulaDatabase : RoomDatabase() {
    abstract fun formulaDao(): FormulaDao
    
    companion object {
        @Volatile
        private var INSTANCE: FormulaDatabase? = null
        
        fun getDatabase(context: Context, scope: CoroutineScope): FormulaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FormulaDatabase::class.java,
                    "formula_database"
                ).addCallback(FormulaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class FormulaDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.formulaDao())
                    }
                }
            }
        }
        
        private suspend fun populateDatabase(formulaDao: FormulaDao) {
            // Delete all content here
            // formulaDao.deleteAll() // You'd need to add this method to DAO
            
            // Add sample formulas
            val sampleFormulas = getSampleFormulas()
            formulaDao.insertFormulas(sampleFormulas)
        }
        
        private fun getSampleFormulas(): List<Formula> {
            return listOf(
                // Algebra
                Formula(
                    name = "Quadratic Formula",
                    formula = "x = (-b ± √(b² - 4ac)) / (2a)",
                    description = "Solves quadratic equations of the form ax² + bx + c = 0",
                    category = FormulaCategory.ALGEBRA.displayName,
                    subcategory = "Quadratic Equations",
                    concept = "A quadratic equation is a polynomial equation of degree 2. The quadratic formula provides the roots of any quadratic equation by using the coefficients a, b, and c.",
                    tips = "• Always check that a ≠ 0 before using\n• The discriminant (b² - 4ac) tells you about the nature of roots\n• If discriminant > 0: two real roots\n• If discriminant = 0: one repeated root\n• If discriminant < 0: no real roots",
                    tricks = "• Remember 'negative b plus or minus' to avoid sign errors\n• Factor out common terms first if possible\n• Complete the square as an alternative method\n• Use the sum and product of roots: sum = -b/a, product = c/a",
                    examples = "Example: 2x² + 3x - 1 = 0\na = 2, b = 3, c = -1\nx = (-3 ± √(9 + 8)) / 4 = (-3 ± √17) / 4",
                    difficulty = DifficultyLevel.MEDIUM.displayName,
                    tags = "quadratic, algebra, roots, polynomial",
                    mathJaxFormula = "x = \\frac{-b \\pm \\sqrt{b^2 - 4ac}}{2a}",
                    variables = "a: coefficient of x²\nb: coefficient of x\nc: constant term\nx: unknown variable",
                    units = "Dimensionless (depends on context)"
                ),
                
                // Geometry
                Formula(
                    name = "Pythagorean Theorem",
                    formula = "a² + b² = c²",
                    description = "Relates the sides of a right triangle",
                    category = FormulaCategory.GEOMETRY.displayName,
                    subcategory = "Triangles",
                    concept = "In a right triangle, the square of the hypotenuse equals the sum of squares of the other two sides. This fundamental theorem has countless applications in geometry and physics.",
                    tips = "• Only applies to right triangles\n• c is always the hypotenuse (longest side)\n• Can be used to find any side if the other two are known\n• Works in any unit system as long as all measurements use the same unit",
                    tricks = "• Pythagorean triples: (3,4,5), (5,12,13), (8,15,17), (7,24,25)\n• Scale these triples for quick calculations\n• Use to check if a triangle is right-angled\n• Extend to 3D: a² + b² + c² = d² for space diagonal",
                    examples = "Example: Triangle with sides 3 and 4\nc² = 3² + 4² = 9 + 16 = 25\nc = 5",
                    difficulty = DifficultyLevel.EASY.displayName,
                    tags = "pythagoras, triangle, right angle, geometry",
                    mathJaxFormula = "a^2 + b^2 = c^2",
                    variables = "a, b: lengths of legs\nc: length of hypotenuse",
                    units = "Length units (m, cm, ft, etc.)"
                ),
                
                // Calculus
                Formula(
                    name = "Derivative of Power Function",
                    formula = "d/dx(x^n) = nx^(n-1)",
                    description = "Power rule for differentiation",
                    category = FormulaCategory.CALCULUS.displayName,
                    subcategory = "Derivatives",
                    concept = "The power rule is one of the most fundamental rules in calculus. It states that the derivative of x raised to a power n is n times x raised to the power (n-1).",
                    tips = "• Works for any real number n\n• Reduce the power by 1 and multiply by the original power\n• Can be combined with chain rule for composite functions\n• Remember the derivative of a constant is 0",
                    tricks = "• For x^(-1), derivative is -x^(-2) = -1/x²\n• For √x = x^(1/2), derivative is (1/2)x^(-1/2) = 1/(2√x)\n• For constants: d/dx(cx^n) = cnx^(n-1)\n• Combine with sum rule: d/dx(u + v) = du/dx + dv/dx",
                    examples = "Example: f(x) = x³\nf'(x) = 3x²\n\nExample: f(x) = 5x⁴\nf'(x) = 20x³",
                    difficulty = DifficultyLevel.MEDIUM.displayName,
                    tags = "calculus, derivative, power rule, differentiation",
                    mathJaxFormula = "\\frac{d}{dx}(x^n) = nx^{n-1}",
                    variables = "x: independent variable\nn: power (any real number)",
                    units = "Depends on context"
                ),
                
                // Trigonometry
                Formula(
                    name = "Sine Rule",
                    formula = "a/sin(A) = b/sin(B) = c/sin(C)",
                    description = "Relates sides and angles in any triangle",
                    category = FormulaCategory.TRIGONOMETRY.displayName,
                    subcategory = "Triangle Laws",
                    concept = "The sine rule states that the ratio of each side of a triangle to the sine of its opposite angle is constant for all three sides. This is essential for solving triangles when you know some sides and angles.",
                    tips = "• Works for any triangle, not just right triangles\n• Use when you know two angles and one side, or two sides and one angle\n• Be careful of the ambiguous case (SSA)\n• Can be rearranged to find angles or sides",
                    tricks = "• Reciprocal form: sin(A)/a = sin(B)/b = sin(C)/c\n• Use with angle sum: A + B + C = 180°\n• For area: Area = (1/2)ab sin(C)\n• Check your answer using the triangle inequality",
                    examples = "Example: Triangle with a = 5, A = 30°, B = 45°\nC = 180° - 30° - 45° = 105°\nb = a × sin(B)/sin(A) = 5 × sin(45°)/sin(30°) = 5√2",
                    difficulty = DifficultyLevel.MEDIUM.displayName,
                    tags = "trigonometry, sine rule, triangle, angles",
                    mathJaxFormula = "\\frac{a}{\\sin A} = \\frac{b}{\\sin B} = \\frac{c}{\\sin C}",
                    variables = "a, b, c: side lengths\nA, B, C: opposite angles",
                    units = "Length units for sides, degrees/radians for angles"
                ),
                
                // Statistics
                Formula(
                    name = "Standard Deviation",
                    formula = "σ = √(Σ(xi - μ)²/N)",
                    description = "Measures spread of data around the mean",
                    category = FormulaCategory.STATISTICS.displayName,
                    subcategory = "Descriptive Statistics",
                    concept = "Standard deviation quantifies how spread out data points are from the mean. A small standard deviation indicates data points are close to the mean, while a large standard deviation indicates data points are spread out.",
                    tips = "• Always non-negative\n• Same units as the original data\n• Use sample standard deviation (N-1) for samples\n• Population standard deviation uses N for populations\n• About 68% of data falls within 1 standard deviation of the mean",
                    tricks = "• Alternative formula: σ = √(Σxi²/N - μ²)\n• For normal distribution: ~68% within 1σ, ~95% within 2σ, ~99.7% within 3σ\n• Coefficient of variation: CV = σ/μ\n• Variance = σ²",
                    examples = "Example: Data set {2, 4, 6, 8}\nMean μ = 5\nσ = √((9+1+1+9)/4) = √(20/4) = √5 ≈ 2.24",
                    difficulty = DifficultyLevel.MEDIUM.displayName,
                    tags = "statistics, standard deviation, spread, variance",
                    mathJaxFormula = "\\sigma = \\sqrt{\\frac{\\sum_{i=1}^{N}(x_i - \\mu)^2}{N}}",
                    variables = "σ: standard deviation\nxi: individual data points\nμ: population mean\nN: number of data points",
                    units = "Same as original data"
                ),
                
                // Physics
                Formula(
                    name = "Newton's Second Law",
                    formula = "F = ma",
                    description = "Relates force, mass, and acceleration",
                    category = FormulaCategory.PHYSICS.displayName,
                    subcategory = "Mechanics",
                    concept = "Newton's second law states that the net force acting on an object is equal to the product of its mass and acceleration. This is one of the fundamental laws of classical mechanics.",
                    tips = "• F is the net force (sum of all forces)\n• Mass is constant for most problems\n• Acceleration is in the same direction as net force\n• Use consistent units (SI: N, kg, m/s²)\n• Consider all forces acting on the object",
                    tricks = "• F = dp/dt (force equals rate of change of momentum)\n• For constant mass: F = m(dv/dt)\n• Weight: W = mg (special case where a = g)\n• For multiple forces: ΣF = ma\n• Vector equation: F⃗ = ma⃗",
                    examples = "Example: 5 kg object with 10 N force\na = F/m = 10/5 = 2 m/s²\n\nExample: Finding force to accelerate 2 kg at 3 m/s²\nF = 2 × 3 = 6 N",
                    difficulty = DifficultyLevel.EASY.displayName,
                    tags = "physics, newton, force, mass, acceleration",
                    mathJaxFormula = "F = ma",
                    variables = "F: net force\nm: mass\na: acceleration",
                    units = "F: Newtons (N), m: kilograms (kg), a: m/s²"
                )
            )
        }
    }
}