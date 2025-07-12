package com.example.mathformula

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.example.mathformula.ui.theme.MathFormulaTheme
import com.example.mathformula.ui.navigation.MathNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathFormulaTheme {
                MathNavHost()
            }
        }
    }
}