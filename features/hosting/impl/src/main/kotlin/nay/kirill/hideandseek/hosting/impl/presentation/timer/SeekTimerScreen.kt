package nay.kirill.hideandseek.hosting.impl.presentation.timer

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
import nay.kirill.hideandseek.hosting.impl.R

@Composable
fun SeekTimerScreen(
        state: SeekTimerUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit
) {
    when (state) {
        is SeekTimerUiState.Error -> AppError(
                errorDescription = stringResource(id = state.descriptionId),
                backAction = onBack,
                retryAction = onRetry
        )
        is SeekTimerUiState.Content -> Content(state, onBack)
    }
}

@Composable
private fun Content(
        state: SeekTimerUiState.Content,
        onBack: () -> Unit,
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.seek_timer_title))
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
                    state = AppButtonState.Content(text = stringResource(R.string.stop_game_button_title)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onBack
            )
        }
    }
}