package com.stivosha.habit_app

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class ComposeAddScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
): ComposeScreen<ComposeAddScreen>(
    semanticsProvider = semanticsProvider,
    viewBuilderAction = { hasTestTag("AddScreen") }
) {
    val title: KNode = child {
        hasTestTag("titleAddScreen")
    }
    val description: KNode = child {
        hasTestTag("descriptionAddScreen")
    }
    val addScreenAddButton: KNode = child {
        hasTestTag("addScreenAddButton")
    }
}