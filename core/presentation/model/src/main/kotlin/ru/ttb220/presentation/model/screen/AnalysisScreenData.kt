package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.AnalysisData

@Immutable
class AnalysisScreenData(
    val startDate: String,
    val endDate: String,
    val amount: String,
    val analysed: List<AnalysisData>
)