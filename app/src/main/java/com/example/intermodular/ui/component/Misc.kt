package com.example.intermodular.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun PredefinedSpacer(size: Dp = 10.dp) {
    Row(){
        Spacer(modifier= Modifier.size(size))
    }
}