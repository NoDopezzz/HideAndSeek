package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.Manifest.permission.BLUETOOTH_CONNECT
import androidx.annotation.RequiresPermission
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.sessionsearch.impl.R
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

internal class SessionSearchStateConverter : (SessionSearchState) -> SessionSearchUiState {

    @RequiresPermission(BLUETOOTH_CONNECT)
    override fun invoke(state: SessionSearchState): SessionSearchUiState = when (state.devicesEvent) {
        is ContentEvent.Error -> SessionSearchUiState.Error(
                titleId = R.string.session_search_error_title,
                subtitleId = R.string.session_search_error_subtitle
        )
        else -> SessionSearchUiState.Content(
                titleId = R.string.session_search_title,
                subtitleId = R.string.session_search_subtitle,
                sessions = state.devicesEvent.data
                        ?.map {
                            SessionUiState(
                                    name = it.name,
                                    isLoading = false
                            )
                        }
                        .orEmpty()
        )
    }
}