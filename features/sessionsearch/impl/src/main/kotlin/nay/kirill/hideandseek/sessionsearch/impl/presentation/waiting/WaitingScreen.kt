package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.core.ui.error.AppError
import nay.kirill.hideandseek.sessionsearch.impl.R

@Composable
internal fun WaitingScreen(
        state: WaitingUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit
) {
    when (state) {
        is WaitingUiState.Content -> Content(state, onBack)
        else -> AppError(
                errorDescription = stringResource(id = R.string.waiting_screen_error_description),
                backAction = onBack,
                retryAction = onRetry
        )
    }
}

@Composable
private fun Content(
        state: WaitingUiState.Content,
        onBack: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.waiting_screen_title))
            }
    ) { paddingValues ->
        Column(
                modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding() + 18.dp)
                        .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1F))
            SandClock(
                    modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(200.dp)
            )
            Spacer(modifier = Modifier.weight(1F))

            val text = buildAnnotatedString {
                withStyle(AppTextStyle.SubTitle.toSpanStyle()) {
                    append(stringResource(id = R.string.waiting_screen_description))
                }
                withStyle(AppTextStyle.SubTitleHighlighted.toSpanStyle()) {
                    append(" ${state.deviceName}")
                }
            }
            Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp, end = 32.dp)
            )
            Spacer(modifier = Modifier.height(42.dp))
            AppButton(
                    state = AppButtonState.Content(text = stringResource(id = R.string.waiting_back_button)),
                    onClick = onBack,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun SandClock(
        modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sand_clock))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
            modifier = modifier,
            composition = composition,
            progress = { progress },
    )
}

@Preview
@Composable
private fun WaitingScreenPreview(
    @PreviewParameter(WaitingUiStateProvider::class) state: WaitingUiState
) {
    WaitingScreen(
            state = state,
            onBack = { },
            onRetry = { }
    )
}

internal class WaitingUiStateProvider : PreviewParameterProvider<WaitingUiState> {

    override val values = sequenceOf(
            WaitingUiState.Content(deviceName = "Кирилл's S22")
    )

}