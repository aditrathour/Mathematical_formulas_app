package com.example.mathformula.data.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = FormulaEntity::class)
@Entity(tableName = "formulas_fts")
data class FormulaFts(
    val title: String,
    val latex: String,
    val explanation: String,
    val tips: String?
)