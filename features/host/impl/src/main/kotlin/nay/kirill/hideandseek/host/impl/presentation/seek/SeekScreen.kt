package nay.kirill.hideandseek.host.impl.presentation.seek

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.compose.AppColors
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.core.ui.error.AppError
import nay.kirill.core.ui.radar.RadarView
import nay.kirill.hideandseek.host.impl.R

@Composable
internal fun SeekScreen(
        state: SeekUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit,
        onPhoto: () -> Unit,
        onLocation: () -> Unit,
        onScan: (String) -> Unit
) {
    when (state) {
        is SeekUiState.Error -> AppError(
                errorDescription = stringResource(id = state.descriptionId),
                backAction = onBack,
                retryAction = onRetry
        )
        is SeekUiState.Content -> Content(state, onBack, onPhoto, onLocation, onScan)
    }
}

@Composable
private fun Content(
        state: SeekUiState.Content,
        onBack: () -> Unit,
        onPhoto: () -> Unit,
        onLocation: () -> Unit,
        onScan: (String) -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.seek_title))
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding() + 16.dp)
                        .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1F))

            CenterView(
                    state = state,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onScan = onScan
            )
            Spacer(modifier = Modifier.weight(1F))

            val text = buildAnnotatedString {
                withStyle(AppTextStyle.SubTitle.toSpanStyle()) {
                    append(stringResource(id = R.string.left_to_find))
                }
                withStyle(AppTextStyle.SubTitleHighlighted.toSpanStyle()) {
                    append(" ${state.devicesLeft}")
                }
            }
            Row(
                    modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                        text = text,
                        modifier = Modifier
                                .padding(start = 16.dp, end = 32.dp)
                                .align(Alignment.Bottom)
                )
                Spacer(modifier = Modifier.weight(1F))
                ImageButton(state, onPhoto, onLocation)
            }

            Spacer(modifier = Modifier.height(42.dp))
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

@Composable
private fun CenterView(
        state: SeekUiState.Content,
        modifier: Modifier = Modifier,
        onScan: (String) -> Unit
) {
    when {
        state.isScanningView -> ScanningView(onScan = onScan)
        state.currentLocation != null -> RadarView(
                centerLocation = state.currentLocation,
                locations = state.locations,
                size = 300.dp,
                modifier = modifier
        )
        else -> Unit
    }
}

@Composable
private fun ImageButton(
        state: SeekUiState.Content,
        onPhoto: () -> Unit,
        onLocation: () -> Unit
) {
    if (state.showButton) {
        Image(
                painter = if (state.isScanningView) {
                    painterResource(id = R.drawable.icon_location)
                } else {
                    painterResource(id = R.drawable.icon_photo)
                },
                contentDescription = "",
                modifier = Modifier
                        .padding(end = 32.dp)
                        .clickable(
                                onClick = if (state.isScanningView) onLocation else onPhoto,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                        bounded = false,
                                        color = AppColors.TransparentPrimary
                                )
                        )
        )
    }
}

@Composable
private fun ScanningView(
        modifier: Modifier = Modifier,
        onScan: (String) -> Unit
) {
    // TODO add scanning
}

@Preview
@Composable
private fun SeekScreenPreview(
        @PreviewParameter(SeekStateProvider::class) state: SeekUiState
) {
    SeekScreen(
            state = state,
            onBack = { },
            onRetry = { },
            onPhoto = { },
            onLocation = { },
            onScan = { }
    )
}

internal class SeekStateProvider : PreviewParameterProvider<SeekUiState> {

    override val values: Sequence<SeekUiState> = sequenceOf(
            SeekUiState.Error(descriptionId = R.string.common_error_description),
            SeekUiState.Content(
                    locations = listOf(),
                    devicesLeft = 3,
                    currentLocation = null,
                    showButton = true
            )
    )

}