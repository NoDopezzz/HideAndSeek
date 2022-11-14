package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.bluetooth.BluetoothDevice
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState

internal data class SessionSearchState(
        val devicesEvent: ContentEvent<List<BluetoothDevice>>
)

internal sealed interface SessionSearchUiState {

    val titleId: Int

    val subtitleId: Int

    data class Content(
            val sessions: List<SessionUiState>,
            override val titleId: Int,
            override val subtitleId: Int
    ) : SessionSearchUiState

    data class Error(
            override val titleId: Int,
            override val subtitleId: Int
    ) : SessionSearchUiState

}
