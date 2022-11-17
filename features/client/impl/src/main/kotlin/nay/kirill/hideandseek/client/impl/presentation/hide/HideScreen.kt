package nay.kirill.hideandseek.client.impl.presentation.hide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.core.ui.error.AppError
import nay.kirill.hideandseek.client.impl.R

@Composable
fun HideScreen(
        state: HideUiState,
        onRetry: () -> Unit,
        onBack: () -> Unit
) {
    when (state) {
        is HideUiState.Error -> AppError(
                errorDescription = stringResource(id = R.string.common_error_description),
                backAction = onBack,
                retryAction = onRetry
        )
        is HideUiState.Content -> Content(state, onBack)
    }

}

@Composable
private fun Content(
    state: HideUiState.Content,
    onBack: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.hide_timer_title))
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding() + 16.dp)
                        .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                    text = stringResource(id = R.string.hide_screen_description),
                    modifier = Modifier.padding(start = 16.dp, end = 52.dp),
                    style = AppTextStyle.SubTitle
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

@Preview
@Composable
private fun HideScreenPreview(
        @PreviewParameter(HideStateProvider::class) state: HideUiState
) {
    HideScreen(
            state = state,
            onRetry = { },
            onBack = { }
    )
}

internal class HideStateProvider : PreviewParameterProvider<HideUiState> {

    override val values: Sequence<HideUiState> = sequenceOf(
            HideUiState.Error,
            HideUiState.Content(deviceAddress = "6F:5F:98:9B:B8:90")
    )

}