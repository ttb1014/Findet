package ru.ttb220.account.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.account.presentation.viewmodel.AccountViewModel
import ru.ttb220.designsystem.topappbar.EditableTopAppBar
import ru.ttb220.presentation.model.R

@Composable
fun AccountEditableTopAppBar(
    lastSyncFormattedTime: String,
    isConnected: Boolean,
    modifier: Modifier = Modifier,
    accountViewModel: AccountViewModel,
    hideTabCallback: () -> Unit = {},
) {
    val currentAccountName: String? by accountViewModel.accountNameFlow.collectAsStateWithLifecycle(
        initialValue = null
    )

    val defaultAccountName = stringResource(R.string.my_account)

    var editedText: String by remember(
        currentAccountName
    ) {
        mutableStateOf(currentAccountName ?: defaultAccountName)
    }

    EditableTopAppBar(
        text = editedText,
        modifier = modifier,
        onLeadingIconClick = hideTabCallback,
        onTrailingIconClick = {
            accountViewModel.updateAccountName(
                editedText,
                afterEdited = hideTabCallback
            )
        },
        onTextEdited = {
            editedText = it
        },
        onInputFinished = {
            accountViewModel.updateAccountName(
                editedText,
                afterEdited = hideTabCallback
            )
        },
        lastSyncFormattedTime = lastSyncFormattedTime,
        isConnected = isConnected,
    )
}