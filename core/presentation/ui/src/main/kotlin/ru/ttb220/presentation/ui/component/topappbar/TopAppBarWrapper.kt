package ru.ttb220.presentation.ui.component.topappbar

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.ttb220.presentation.model.R

@Suppress("FunctionName", "LongParameterList")
@Composable
fun TopAppBarWrapper(
    defaultText: String,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    isInEditMode: Boolean = false,
    onTextEdited: (String) -> Unit = {},
    onInputFinished: () -> Unit = {},
) {
    when (isInEditMode) {
        true -> {
            val editText by remember {
                mutableStateOf(defaultText)
            }

            EditableTopAppBar(
                text = editText,
                modifier = modifier,
                leadingIcon = R.drawable.cross,
                trailingIcon = R.drawable.check,
                onTextEdited = onTextEdited,
                onLeadingIconClick = onLeadingIconClick,
                onTrailingIconClick = onTrailingIconClick,
                onInputFinished = onInputFinished
            )
        }

        false -> TopAppBar(
            text = defaultText,
            modifier = modifier,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            onLeadingIconClick = onLeadingIconClick,
            onTrailingIconClick = onTrailingIconClick
        )
    }
}