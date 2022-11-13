package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.bluetooth.BluetoothDevice
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

internal data class SessionSearchState(
        val devices: ContentEvent<List<BluetoothDevice>>
)

internal sealed interface SessionSearchUiState {

    object Loading : SessionSearchUiState

    data class Content(
            val sessions: List<SessionUiState>
    ) : SessionSearchUiState

    object Error : SessionSearchUiState

}
