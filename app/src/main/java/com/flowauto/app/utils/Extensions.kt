package com.flowauto.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextUnit.toDp(): Dp = with(LocalDensity.current) {
    this@toDp.toDp()
}

fun <T> List<T>.getOrEmpty(): List<T> {
    return this.ifEmpty { emptyList() }
}

fun String.isValidEmail(): Boolean {
    return this.contains("@") && this.contains(".")
}

fun String.truncate(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.take(maxLength) + "..."
    } else {
        this
    }
}
