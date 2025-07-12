package com.example.mathformula.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.kexanie.library.MathView

@Composable
fun LatexBlock(
    latex: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            MathView(context).apply {
                // Disable zoom & scrolling for a minimal view
                settings.javaScriptEnabled = true
                // Display block mode $$...$$
                text = "$$$latex$$"
                webViewClient = WebViewClient()
            }
        },
        update = { view ->
            (view as? MathView)?.text = "$$$latex$$"
        },
        modifier = modifier
    )
}