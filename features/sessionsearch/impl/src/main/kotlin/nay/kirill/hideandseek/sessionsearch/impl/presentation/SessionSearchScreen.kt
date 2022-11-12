package nay.kirill.hideandseek.sessionsearch.impl.presentation

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
import nay.kirill.hideandseek.sessionsearch.impl.R

@Composable
internal fun SessionSearchScreen(
        state: SessionSearchUiState,
        onBack: () -> Unit
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

            val textId = when (state) {
                is SessionSearchUiState.Error -> R.string.session_search_error_subtitle
                else -> R.string.session_search_subtitle
            }
            Text(
                    text = stringResource(id = textId),
                    style = AppTextStyle.SubTitle,
                    modifier = Modifier
                            .padding(start = 16.dp, end = 52.dp)
            )
            Spacer(modifier = Modifier.weight(1F))
            AppButton(
                    state = AppButtonState.Content(text = stringResource(R.string.back_button)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onBack
            )
        }
    }
}

@Composable
@Preview
private fun SessionsSearchPreview(
        @PreviewParameter(SessionsSearchStateProvider::class) state: SessionSearchUiState
) {
    SessionSearchScreen(state) {

    }
}

internal class SessionsSearchStateProvider : PreviewParameterProvider<SessionSearchUiState> {

    override val values: Sequence<SessionSearchUiState> = sequenceOf(
            SessionSearchUiState.Content(
                    sessions = listOf("1", "2", "3")
            ),
            SessionSearchUiState.Error,
            SessionSearchUiState.Loading
    )

}