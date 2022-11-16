package nay.kirill.hideandseek.sessionsearch.impl.presentation.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.core.ui.error.AppError
import nay.kirill.core.ui.timer.Timer
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
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.hide_counter_title))
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding() + 16.dp)
                        .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Timer(
                    state = state.timerState,
                    size = 250,
                    stroke = 10,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(1F))
            AppButton(
                    state = AppButtonState.Content(text = stringResource(R.string.quit_game_button_title)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onBack
            )
        }
    }
}