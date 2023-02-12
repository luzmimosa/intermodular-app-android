package com.example.intermodular.ui.component.global

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorSupporterTextField(
    label: String,
    value: String,
    errorVisible: Boolean,
    errorMessage: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    Column {
        Row {
            TextField(
                value = value,
                onValueChange = { onValueChange(it) },
                singleLine = singleLine,
                label = { Text(text = label) },
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation,
            )
        }

        if (errorVisible) {
            Row (
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp
                )
            }
        }
    }
}