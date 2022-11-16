package nay.kirill.hideandseek.sessionsearch.impl.presentation.counter

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nay.kirill.core.ui.error.AppError
import nay.kirill.hideandseek.sessionsearch.impl.R

@Composable
internal fun HideCounterScreen(
        state: HideCounterUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit
) {
    when (state) {
        is HideCounterUiState.Content -> Content(state, onBack)
        is HideCounterUiState.Error -> AppError(
                errorDescription = stringResource(id = R.string.waiting_screen_error_description),
                backAction = onBack,
                retryAction = onRetry
        )
    }
}

@Composable
private fun Content(
        state: HideCounterUiState.Content,
        onBack: () -> Unit,
) {

}