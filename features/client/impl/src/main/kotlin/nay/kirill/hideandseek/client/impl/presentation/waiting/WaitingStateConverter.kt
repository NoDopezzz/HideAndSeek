package nay.kirill.hideandseek.client.impl.presentation.waiting

import android.annotation.SuppressLint

internal class WaitingStateConverter : (WaitingState) -> WaitingUiState {

    @SuppressLint("MissingPermission")
    override fun invoke(state: WaitingState): WaitingUiState = when (state) {
        is WaitingState.Content -> WaitingUiState.Content(
                deviceName = state.serverDevice?.name ?: state.serverDevice?.address ?: ""
        )
        is WaitingState.Error -> WaitingUiState.Error
    }
}