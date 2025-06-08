package ru.ttb220.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.ttb220.mock.mockExpenseResources
import ru.ttb220.mock.mockTotalExpenses
import ru.ttb220.ui.R
import ru.ttb220.ui.component.BottomBar
import ru.ttb220.ui.component.ButtonCircle
import ru.ttb220.ui.component.ExpensesList
import ru.ttb220.ui.component.TopAppBar
import ru.ttb220.ui.model.DestinationResource
import ru.ttb220.ui.model.TopLevelDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // TODO: change color of status and navigation bars
            Scaffold(
                modifier = Modifier,
                topBar = {
                    TopAppBar(
                        text = "Расходы сегодня",
                        leadingIcon = null,
                        trailingIcon = R.drawable.history,
                        modifier = Modifier
                            .windowInsetsPadding(
                                WindowInsets.systemBars
                                    .only(WindowInsetsSides.Top)
                            )
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
                            .windowInsetsPadding(
                                WindowInsets.systemBars
                                    .only(WindowInsetsSides.Bottom)
                            )
                    )
                },
                // TODO: investigate whether it places button correctly according to design (surely no)
                floatingActionButton = { ButtonCircle() },
                floatingActionButtonPosition = FabPosition.End,
                containerColor = MaterialTheme.colorScheme.surface,
            ) { padding ->
                ExpensesList(
                    expenses = mockExpenseResources,
                    expensesTotal = mockTotalExpenses,
                    modifier = Modifier
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.systemBars
                        ),
                )
            }
        }
    }
}