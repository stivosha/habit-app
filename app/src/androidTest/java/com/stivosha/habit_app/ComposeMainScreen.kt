package com.stivosha.habit_app

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class ComposeMainScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
): ComposeScreen<ComposeMainScreen>(
    semanticsProvider = semanticsProvider,
    viewBuilderAction = { hasTestTag("MainScreen") }
) {
    val addButton: KNode = child {
        hasTestTag("MainScreenAdd")
    }
}