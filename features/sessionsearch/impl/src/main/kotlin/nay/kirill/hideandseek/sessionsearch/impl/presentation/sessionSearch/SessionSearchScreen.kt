package nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import nay.kirill.core.ui.list.AppList
import nay.kirill.core.ui.res.R as CoreR
import nay.kirill.hideandseek.sessionsearch.impl.R
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionElement
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

@Composable
internal fun SessionSearchScreen(
        state: SessionSearchUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit,
        onConnectToDevice: (String) -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = state.titleId))
            }
    ) {
        Column(
                modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding() + 18.dp)
                        .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                    text = stringResource(id = state.subtitleId),
                    style = AppTextStyle.SubTitle,
                    modifier = Modifier
                            .padding(start = 16.dp, end = 52.dp)
            )

            if (state is SessionSearchUiState.Content) {
                Content(
                        sessions = state.sessions,
                        modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .weight(1F),
                        onConnectToDevice = onConnectToDevice
                )
            } else {
                Spacer(modifier = Modifier.weight(1F))
            }

            if (state is SessionSearchUiState.Error) {
                AppButton(
                        state = AppButtonState.Content(text = stringResource(CoreR.string.retry_button)),
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        onClick = onRetry
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
            AppButton(
                    state = AppButtonState.Content(text = stringResource(CoreR.string.back_button)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onBack
            )
        }
    }
}

@Composable
private fun Content(
        sessions: List<SessionUiState>,
        modifier: Modifier = Modifier,
        onConnectToDevice: (String) -> Unit
) {
    AppList(
            listTitle = stringResource(id = R.string.available_sessions),
            elements = sessions,
            modifier = modifier
    ) { state ->
        SessionElement(state) {
            onConnectToDevice.invoke(state.deviceAddress)
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
            onRetry = { },
            onConnectToDevice = { }
    )
}

internal class SessionsSearchStateProvider : PreviewParameterProvider<SessionSearchUiState> {

    override val values: Sequence<SessionSearchUiState> = sequenceOf(
            SessionSearchUiState.Content(
                    sessions = listOf(
                            SessionUiState("", "Кирилл Samsung S22"),
                            SessionUiState("", "Дима Xaomi MI6", isLoading = true),
                            SessionUiState("", "Дима Huawei P40")
                    ),
                    titleId = R.string.session_search_title,
                    subtitleId = R.string.session_search_subtitle
            ),
            SessionSearchUiState.Error(
                    titleId = R.string.session_search_error_title,
                    subtitleId = R.string.session_search_error_subtitle
            )
    )

}