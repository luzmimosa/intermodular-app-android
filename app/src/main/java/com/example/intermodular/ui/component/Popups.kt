package com.example.intermodular.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun InputPopup(
    message: String,
    label: String,
    submitButtonLabel: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    canSubmit: (current: String) -> Boolean = {true},
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {

    var input by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {onDismiss()},
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = message,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    TextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text(text = label) },
                        keyboardOptions = keyboardOptions
                    )
                }

                Row(modifier = Modifier.padding(8.dp)) {
                    Button(
                        onClick = {onSubmit(input)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        enabled = canSubmit(input)
                    ) {

                        Text(
                            text = submitButtonLabel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SimplePopup(
    message: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = message,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun LoadingPopup(
    message: String
) {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = message,
                        textAlign = TextAlign.Center
                    )
                }
                Row {
                    CircularProgressIndicator()
                }
            }
        }
    }
}