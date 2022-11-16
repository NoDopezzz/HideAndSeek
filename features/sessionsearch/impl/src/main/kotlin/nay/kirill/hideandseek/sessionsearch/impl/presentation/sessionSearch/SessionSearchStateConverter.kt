package nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch

import android.annotation.SuppressLint
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.sessionsearch.impl.R
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

internal class SessionSearchStateConverter : (SessionSearchState) -> SessionSearchUiState {

    @SuppressLint("MissingPermission")
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
                                    deviceAddress = it.address,
                                    name = it.name ?: it.address,
                                    isLoading = it.address == state.deviceAddressToConnect
                            )
                        }
                        .orEmpty()
        )
    }
}