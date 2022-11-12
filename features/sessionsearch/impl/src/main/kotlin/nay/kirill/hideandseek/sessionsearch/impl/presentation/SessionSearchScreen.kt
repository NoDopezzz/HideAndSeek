package nay.kirill.hideandseek.sessionsearch.impl.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SessionSearchScreen(
    state: SessionSearchUiState
) {
    Text(text = "Counter: ${state.counter}")
}