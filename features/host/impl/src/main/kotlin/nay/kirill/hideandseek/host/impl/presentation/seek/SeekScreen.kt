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
import nay.kirill.core.ui.radar.RadarLocation
import nay.kirill.core.ui.radar.RadarView
import nay.kirill.hideandseek.host.impl.R

@Composable
internal fun SeekScreen(
        state: SeekUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit,
        onPhoto: () -> Unit
) {
    when (state) {
        is SeekUiState.Error -> AppError(
                errorDescription = stringResource(id = state.descriptionId),
                backAction = onBack,
                retryAction = onRetry
        )
        is SeekUiState.Content -> Content(state, onBack, onPhoto)
    }
}

@Composable
private fun Content(
        state: SeekUiState.Content,
        onBack: () -> Unit,
        onPhoto: () -> Unit
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

            RadarView(
                    centerLocation = state.currentLocation ?: RadarLocation(0F, 0F),
                    locations = state.locations,
                    size = 300.dp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
                if (state.showCamera) {
                    Image(
                            painter = painterResource(id = R.drawable.icon_photo),
                            contentDescription = "",
                            modifier = Modifier
                                    .padding(end = 32.dp)
                                    .clickable(
                                            onClick = onPhoto,
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(
                                                    bounded = false,
                                                    color = AppColors.TransparentPrimary
                                            )
                                    )
                    )
                }
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

@Preview
@Composable
private fun SeekScreenPreview(
        @PreviewParameter(SeekStateProvider::class) state: SeekUiState
) {
    SeekScreen(
            state = state,
            onBack = { },
            onRetry = { },
            onPhoto = { }
    )
}

internal class SeekStateProvider : PreviewParameterProvider<SeekUiState> {

    override val values: Sequence<SeekUiState> = sequenceOf(
            SeekUiState.Error(descriptionId = R.string.common_error_description),
            SeekUiState.Content(
                    locations = listOf(),
                    devicesLeft = 3,
                    currentLocation = RadarLocation(200F, 200F),
                    showCamera = true
            )
    )

}