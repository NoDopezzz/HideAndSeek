package nay.kirill.hideandseek.sessionsearch.impl.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.compose.AppColors
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.hideandseek.sessionsearch.impl.R
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionElement
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

@Composable
internal fun SessionSearchScreen(
        state: SessionSearchUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.session_search_title))
            }
    ) {
        Column(
                modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding() + 18.dp)
                        .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            val textSubtitleId = when (state) {
                is SessionSearchUiState.Error -> R.string.session_search_error_subtitle
                is SessionSearchUiState.Content -> R.string.session_search_subtitle
                is SessionSearchUiState.Loading -> R.string.session_search_loading_subtitle
            }
            Text(
                    text = stringResource(id = textSubtitleId),
                    style = AppTextStyle.SubTitle,
                    modifier = Modifier
                            .padding(start = 16.dp, end = 52.dp)
            )

            when (state) {
                is SessionSearchUiState.Loading -> {
                    Spacer(modifier = Modifier.weight(1F))
                    CircularProgressIndicator(
                            modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                            color = AppColors.Primary
                    )
                }
                is SessionSearchUiState.Content -> Content(
                        sessions = state.sessions,
                        modifier = Modifier
                                .padding(horizontal = 16.dp)
                )
                else -> Unit
            }

            Spacer(modifier = Modifier.weight(1F))

            val (textButtonId, onClick) = when(state) {
                is SessionSearchUiState.Error -> R.string.retry_button to onRetry
                else -> R.string.back_button to onBack
            }
            AppButton(
                    state = AppButtonState.Content(text = stringResource(textButtonId)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onClick
            )
        }
    }
}

@Composable
private fun Content(
        sessions: List<SessionUiState>,
        modifier: Modifier = Modifier
) {
    Column(modifier) {
        Spacer(modifier = Modifier.height(45.dp))
        Text(
                text = stringResource(id = R.string.available_sessions),
                style = AppTextStyle.ListTitleStyle
        )
        sessions.forEach { state ->
            SessionElement(state) {
                // TODO
            }
        }
    }
}

@Composable
@Preview
private fun SessionsSearchPreview(
        @PreviewParameter(SessionsSearchStateProvider::class) state: SessionSearchUiState
) {
    SessionSearchScreen(
            state = state,
            onBack = { },
            onRetry = { }
    )
}

internal class SessionsSearchStateProvider : PreviewParameterProvider<SessionSearchUiState> {

    override val values: Sequence<SessionSearchUiState> = sequenceOf(
            SessionSearchUiState.Content(
                    sessions = listOf(
                            SessionUiState("Кирилл Samsung S22"),
                            SessionUiState("Дима Xaomi MI6", isLoading = true),
                            SessionUiState("Дима Huawei P40")
                    )
            ),
            SessionSearchUiState.Error,
            SessionSearchUiState.Loading
    )

}