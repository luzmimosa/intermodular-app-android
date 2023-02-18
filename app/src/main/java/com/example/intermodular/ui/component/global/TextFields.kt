package com.example.intermodular.ui.component.global

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = modifier.fillMaxWidth()) {
            TextField(
                value = value,
                onValueChange = { onValueChange(it) },
                singleLine = singleLine,
                maxLines = maxLines,
                label = { Text(text = label) },
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation,
                modifier = modifier.fillMaxWidth()
            )
        }

        if (errorVisible) {
            Row (
                modifier = Modifier.padding(0.dp)
            ) {
                ErrorText(text = errorMessage)
            }
        }
    }
}

@Composable
fun ErrorText(
    text: String,
){
    Text(
        text = text,
        color = MaterialTheme.colors.error,
        fontSize = 12.sp
    )
}