package ru.ttb220.setting.presentation.model

import androidx.annotation.StringRes
import ru.ttb220.presentation.model.R

// may be converted to nested graph navigation in future
enum class SettingsDestination(
    @StringRes val textId: Int
) {
    PASSWORD(
        R.string.password
    ),
    SYNCHRONIZATION(
        R.string.synchronization
    ),
    LANGUAGE(
        R.string.language
    ),
    INFO(
        R.string.information
    )
}