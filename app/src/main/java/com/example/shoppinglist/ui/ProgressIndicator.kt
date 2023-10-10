package com.example.shoppinglist.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShowCircularProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(size = 64.dp),
        color = Color.Magenta,
        strokeWidth = 6.dp
    )
}