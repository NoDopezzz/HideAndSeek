package nay.kirill.hideandseek.client.impl.presentation.sessionSearch

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
import nay.kirill.core.ui.error.AppError
import nay.kirill.core.ui.list.AppList
import nay.kirill.core.ui.res.R as CoreR
import nay.kirill.hideandseek.client.impl.R
import nay.kirill.hideandseek.client.impl.presentation.views.SessionElement
import nay.kirill.hideandseek.client.impl.presentation.views.SessionUiState

@Composable
internal fun SessionSearchScreen(
        state: SessionSearchUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit,
        onConnectToDevice: (String) -> Unit
) {
    when (state) {
        is SessionSearchUiState.Content -> Content(state, onConnectToDevice, onBack)
        is SessionSearchUiState.Error -> AppError(
                errorDescription = stringResource(id = R.string.session_search_error_subtitle),
                backAction = onBack,
                retryAction = onRetry
        )
    }
}

@Composable
private fun Content(
        state: SessionSearchUiState.Content,
        onConnectToDevice: (String) -> Unit,
        onBack: () -> Unit
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

            AvailableDevices(
                    sessions = state.sessions,
                    modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .weight(1F),
                    isLoadingVisible = state.isLoadingVisible,
                    onConnectToDevice = onConnectToDevice
            )

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
private fun AvailableDevices(
        sessions: List<SessionUiState>,
        isLoadingVisible: Boolean,
        modifier: Modifier = Modifier,
        onConnectToDevice: (String) -> Unit
) {
    AppList(
            listTitle = stringResource(id = R.string.available_sessions),
            elements = sessions,
            isLoadingVisible = isLoadingVisible,
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
                    subtitleId = R.string.session_search_subtitle,
                    isLoadingVisible = false
            ),
            SessionSearchUiState.Error
    )

}