package ru.ttb220.presentation.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation(
    private val currencySymbol: String
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val out = if (text.text.isEmpty()) "" else "${text.text} $currencySymbol"
        val transformedText = AnnotatedString(out)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val max = text.text.length
                return if (offset <= max) offset else max
            }

            override fun transformedToOriginal(offset: Int): Int {
                val max = text.text.length
                return offset.coerceAtMost(max)
            }
        }
        return TransformedText(transformedText, offsetMapping)
    }
}
