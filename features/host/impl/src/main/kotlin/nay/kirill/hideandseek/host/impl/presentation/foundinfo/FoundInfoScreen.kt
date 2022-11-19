package nay.kirill.hideandseek.host.impl.presentation.foundinfo

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
import nay.kirill.hideandseek.host.impl.R

@Composable
internal fun FoundInfoScreen(
        type: FoundType,
        onButtonClick: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.found_info_one_title))
            }
    ) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = it.calculateBottomPadding())
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            val descriptionId = when (type) {
                FoundType.ONE -> R.string.found_info_one_description
                FoundType.ALL -> R.string.found_info_all_description
            }

            Text(
                    text = stringResource(id = descriptionId),
                    style = AppTextStyle.Header2,
                    modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(1F))

            SuccessfulView(
                    modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(400.dp)
            )

            Spacer(modifier = Modifier.weight(1F))

            val buttonTitleId = when (type) {
                FoundType.ONE -> R.string.continue_button_title
                FoundType.ALL -> R.string.main_menu_button_title
            }
            AppButton(
                    state = AppButtonState.Content(text = stringResource(id = buttonTitleId)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onButtonClick
            )
        }
    }
}

@Composable
private fun SuccessfulView(
      modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.successful))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
            modifier = modifier,
            composition = composition,
            progress = { progress },
    )
}
