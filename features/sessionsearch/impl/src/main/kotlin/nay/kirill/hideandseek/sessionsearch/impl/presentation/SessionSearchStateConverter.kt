package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.Manifest.permission.BLUETOOTH_CONNECT
import androidx.annotation.RequiresPermission
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

internal class SessionSearchStateConverter : (SessionSearchState) -> SessionSearchUiState {

    @RequiresPermission(BLUETOOTH_CONNECT)
    override fun invoke(state: SessionSearchState): SessionSearchUiState = when (state.devices) {
        is ContentEvent.Loading -> SessionSearchUiState.Loading
        is ContentEvent.Success -> SessionSearchUiState.Content(
                sessions = state.devices.data.map {
                    SessionUiState(
                            name = it.name,
                            isLoading = false
                    )
                }
        )
        else -> SessionSearchUiState.Error
    }
}