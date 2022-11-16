package nay.kirill.hideandseek.hosting.impl.presentation

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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
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
import nay.kirill.hideandseek.hosting.impl.R
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

@Composable
internal fun HostingScreen(
        state: HostingUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit,
        onStart: () -> Unit
) {
    when (state) {
        is HostingUiState.Content -> Content(state, onBack, onStart)
        is HostingUiState.Error -> AppError(
                errorDescription = stringResource(id = R.string.hosting_error_subtitle),
                backAction = onBack,
                retryAction = onRetry
        )
    }
}

@Composable
private fun Content(
        state: HostingUiState.Content,
        onBack: () -> Unit,
        onStart: () -> Unit
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

            val text = buildAnnotatedString {
                withStyle(AppTextStyle.SubTitle.toSpanStyle()) {
                    append(stringResource(id = state.subtitleId))
                }
                withStyle(AppTextStyle.SubTitleHighlighted.toSpanStyle()) {
                    append(" \"${state.hostDeviceName}\"")
                }
            }
            Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp, end = 52.dp)
            )

            ConnectedDevices(
                    connectedDevices = state.connectedDevices,
                    modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .weight(1F)
            )

            if (state.isPrimaryButtonVisible) {
                AppButton(
                        state = AppButtonState.Content(text = stringResource(id = R.string.start_button_title)),
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        onClick = onStart
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
            AppButton(
                    state = AppButtonState.Content(text = stringResource(id = R.string.back_button_title)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onBack
            )
        }
    }
}

@Composable
private fun ConnectedDevices(
        connectedDevices: List<ConnectedDeviceUiState>,
        modifier: Modifier = Modifier
) {
    AppList(
            listTitle = stringResource(id = R.string.connected_devices_list_title),
            elements = connectedDevices,
            modifier = modifier
    ) { deviceState ->
        Text(
                text = deviceState.name,
                style = AppTextStyle.ListTextStyle,
                modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Preview
@Composable
private fun HostingScreenPreview(
        @PreviewParameter(HostingUiStateProvider::class) state: HostingUiState
) {
    HostingScreen(
            state = state,
            onBack = { },
            onRetry = { },
            onStart = { }
    )
}

internal class HostingUiStateProvider : PreviewParameterProvider<HostingUiState> {

    override val values: Sequence<HostingUiState> = sequenceOf(
            HostingUiState.Error,
            HostingUiState.Content(
                    connectedDevices = listOf(
                            ConnectedDeviceUiState("", "Дима Huawei P40"),
                                    ConnectedDeviceUiState("", "Xaomi M6")
                    ),
                    hostDeviceName = "Кирилл's S22",
                    isPrimaryButtonVisible = true,
                    titleId = R.string.hosting_title,
                    subtitleId = R.string.hosting_subtitle
            )
    )

}