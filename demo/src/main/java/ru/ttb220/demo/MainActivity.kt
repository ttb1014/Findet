package ru.ttb220.demo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import ru.ttb220.mock.mockExpenseResources
import ru.ttb220.mock.mockTotalExpenses
import ru.ttb220.ui.component.TopAppBar
import ru.ttb220.ui.theme.Green
import ru.ttb220.ui.R
import ru.ttb220.ui.component.BottomBar
import ru.ttb220.ui.component.ExpensesList
import ru.ttb220.ui.model.DestinationResource
import ru.ttb220.ui.model.TopLevelDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = ,
                darkScrim = ,
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = ,
                darkScrim = ,
            )
        )
        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.statusBarColor = Green.toArgb()
                window.navigationBarColor = MaterialTheme.colorScheme.surface.toArgb()
            }
            Scaffold(
                modifier = Modifier,
                topBar = {
                    TopAppBar(
                        text = "Расходы сегодня",
                        leadingIcon = null,
                        trailingIcon = R.drawable.history,
                        modifier = Modifier
                    )
                },
                bottomBar = {
                    BottomBar(
                        destinations = TopLevelDestination.entries.map {
                            DestinationResource(
                                destination = it,
                                isActive = false,
                            )
                        },
                        modifier = Modifier
                    )
                },
                containerColor = MaterialTheme.colorScheme.surface,
            ) { padding ->
                ExpensesList(
                    expenses = mockExpenseResources,
                    expensesTotal = mockTotalExpenses,
                    modifier = Modifier
                        .padding(padding)
                )
            }
        }
    }
}