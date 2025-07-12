package com.mathformulas.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "formulas")
@Parcelize
data class Formula(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val formula: String,
    val description: String,
    val category: String,
    val subcategory: String? = null,
    val concept: String,
    val tips: String,
    val tricks: String,
    val examples: String,
    val difficulty: String, // Easy, Medium, Hard
    val tags: String, // Comma-separated tags for search
    val isFavorite: Boolean = false,
    val mathJaxFormula: String? = null, // LaTeX format for rendering
    val variables: String? = null, // Variable explanations
    val units: String? = null, // SI units if applicable
    val relatedFormulas: String? = null // Comma-separated IDs of related formulas
) : Parcelable

enum class FormulaCategory(val displayName: String) {
    ALGEBRA("Algebra"),
    CALCULUS("Calculus"),
    GEOMETRY("Geometry"),
    TRIGONOMETRY("Trigonometry"),
    STATISTICS("Statistics"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    DISCRETE_MATH("Discrete Math"),
    LINEAR_ALGEBRA("Linear Algebra"),
    DIFFERENTIAL_EQUATIONS("Differential Equations"),
    NUMBER_THEORY("Number Theory"),
    PROBABILITY("Probability"),
    COMPLEX_ANALYSIS("Complex Analysis"),
    TOPOLOGY("Topology")
}

enum class DifficultyLevel(val displayName: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    EXPERT("Expert")
}