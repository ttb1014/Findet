package ru.ttb220.designsystem.topappbar

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.model.R
import ru.ttb220.designsystem.theme.Green

@Suppress("LongParameterList", "FunctionNaming")
@Composable
fun EditableTopAppBar(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = R.drawable.cross,
    @DrawableRes trailingIcon: Int? = R.drawable.check,
    onTextEdited: (String) -> Unit = {},
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    onInputFinished: () -> Unit = {},
) {
    BackHandler {
        onLeadingIconClick()
    }

    Surface(
        modifier = modifier,
        color = Green,
    ) {
        Row(
            modifier = modifier
                .windowInsetsPadding(
                    WindowInsets.systemBars
                        .only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
                )
                .height(64.dp)
                .fillMaxWidth()
                .background(Green)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                val interactionSource = remember { MutableInteractionSource() }

                TopAppBarIcon(
                    leadingIcon,
                    MaterialTheme.colorScheme.onSurface,
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onLeadingIconClick
                    )
                )
            } ?: Spacer(Modifier.size(48.dp))


            BasicTextField(
                value = text,
                onValueChange = onTextEdited,
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onSend = {
                        defaultKeyboardAction(ImeAction.Send)
                        onInputFinished()
                    }
                ),
                singleLine = true,
            )

            trailingIcon?.let {
                val interactionSource = remember { MutableInteractionSource() }

                TopAppBarIcon(
                    trailingIcon,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onTrailingIconClick
                    )
                )
            } ?: Spacer(Modifier.size(48.dp))
        }
    }
}

private val keyboardOptions = KeyboardOptions(
    capitalization = KeyboardCapitalization.Sentences,
    autoCorrect = false,
    keyboardType = KeyboardType.Text,
    imeAction = ImeAction.Send,
)


@Composable
private fun TopAppBarIcon(
    @DrawableRes iconId: Int,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = tint
        )
    }
}