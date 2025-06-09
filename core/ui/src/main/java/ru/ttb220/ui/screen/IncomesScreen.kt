package ru.ttb220.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ttb220.model.IncomesScreenResource

@Composable
fun IncomesScreen(
    incomesScreenResource: IncomesScreenResource,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun IncomesScreenPreview() {
    IncomesScreen(
        incomesScreenResource = mockIncomesScreenResource,
    )
}