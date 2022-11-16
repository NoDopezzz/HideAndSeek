package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun WaitingScreen(
        state: WaitingUiState
) {
    when (state) {
        is WaitingUiState.Content -> Text(text = state.deviceName)
        else -> Unit
    }
}