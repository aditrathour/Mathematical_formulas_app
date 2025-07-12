package com.example.mathformula.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FormulaWithCategory(
    @Embedded val formula: FormulaEntity,
    @Relation(parentColumn = "categoryId", entityColumn = "id")
    val category: CategoryEntity
)