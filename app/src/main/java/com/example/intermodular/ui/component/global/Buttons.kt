package com.example.intermodular.ui.component.global

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


@Composable
fun ClickableText(
    text: String,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    underlined: Boolean = true,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = Modifier.clickable { onClick() },
        style = TextStyle(textDecoration = if (underlined) TextDecoration.Underline else TextDecoration.None )
    )
}