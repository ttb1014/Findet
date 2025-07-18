package ru.ttb220.designsystem

import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.ttb220.designsystem.theme.FindetTheme
import ru.ttb220.designsystem.theme.Green
import ru.ttb220.designsystem.theme.GreenHighlight

// TODO: change to custom impl. This one looks different
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onDateSelected: (Long?) -> Unit = {},
) {
    val datePickerState = rememberDatePickerState()

    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                Text("Cancel")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = GreenHighlight,
        ),
    ) {
        DatePicker(
            state = datePickerState,
            modifier = modifier.background(GreenHighlight),
            // some colors are hardcoded as in figma
            colors = DatePickerDefaults.colors(
                containerColor = GreenHighlight,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                headlineContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                navigationContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                yearContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                currentYearContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                selectedYearContainerColor = Green,
                dayContentColor = MaterialTheme.colorScheme.onSurface,
                disabledDayContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedDayContentColor = MaterialTheme.colorScheme.onSurface,
                selectedDayContainerColor = Green,
                todayContentColor = MaterialTheme.colorScheme.onSurface,
                todayDateBorderColor = Color.Transparent,
                dividerColor = Color.Transparent,
            )
        )
    }
}

@Preview
@Composable
private fun DatePickerDialogPreview() {
    FindetTheme {
        DatePickerDialog()
    }
}