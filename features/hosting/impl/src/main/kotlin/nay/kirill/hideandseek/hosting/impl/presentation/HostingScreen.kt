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
import nay.kirill.core.ui.list.AppList
import nay.kirill.hideandseek.hosting.impl.R
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction
import nay.kirill.hideandseek.hosting.impl.presentation.models.ConnectedDeviceUiState

@Composable
internal fun HostingScreen(
        state: HostingUiState,
        onButtonClicked: (ButtonAction) -> Unit,
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

            val text = when (state) {
                is HostingUiState.Content -> buildAnnotatedString {
                    withStyle(AppTextStyle.SubTitle.toSpanStyle()) {
                        append(stringResource(id = state.subtitleId))
                    }
                    withStyle(AppTextStyle.SubTitleHighlighted.toSpanStyle()) {
                        append(" \"${state.hostDeviceName}\"")
                    }
                }
                else -> buildAnnotatedString {
                    withStyle(AppTextStyle.SubTitle.toSpanStyle()) {
                        append(stringResource(id = state.subtitleId))
                    }
                }
            }
            Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp, end = 52.dp)
            )

            if (state is HostingUiState.Content) {
                Content(
                        connectedDevices = state.connectedDevices,
                        modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .weight(1F)
                )
            } else {
                Spacer(modifier = Modifier.weight(1F))
            }

            if (state.isPrimaryButtonVisible) {
                AppButton(
                        state = AppButtonState.Content(text = stringResource(id = state.primaryButtonAction.buttonTitleId)),
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        onClick = { onButtonClicked(state.primaryButtonAction) }
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
            AppButton(
                    state = AppButtonState.Content(text = stringResource(id = state.secondaryButtonAction.buttonTitleId)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = { onButtonClicked(state.secondaryButtonAction) }
            )
        }
    }
}

@Composable
private fun Content(
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
            onButtonClicked = { /* Do nothing */ },
    )
}

internal class HostingUiStateProvider : PreviewParameterProvider<HostingUiState> {

    override val values: Sequence<HostingUiState> = sequenceOf(
            HostingUiState.Error(
                    titleId = R.string.hosting_error_title,
                    subtitleId = R.string.hosting_error_subtitle,
                    primaryButtonAction = ButtonAction.Retry,
                    secondaryButtonAction = ButtonAction.Back
            ),
            HostingUiState.Content(
                    connectedDevices = listOf(
                            ConnectedDeviceUiState("", "Дима Huawei P40"),
                                    ConnectedDeviceUiState("", "Xaomi M6")
                    ),
                    hostDeviceName = "Кирилл's S22",
                    isPrimaryButtonVisible = true,
                    titleId = R.string.hosting_title,
                    subtitleId = R.string.hosting_subtitle,
                    primaryButtonAction = ButtonAction.Start,
                    secondaryButtonAction = ButtonAction.Back
            )
    )

}