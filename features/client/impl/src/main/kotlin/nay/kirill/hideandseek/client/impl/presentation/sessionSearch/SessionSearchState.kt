package nay.kirill.hideandseek.client.impl.presentation.sessionSearch

import android.bluetooth.BluetoothDevice
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.client.impl.presentation.views.SessionUiState

internal data class SessionSearchState(
        val devicesEvent: ContentEvent<List<BluetoothDevice>>,
        val deviceAddressToConnect: String? = null
)

internal sealed interface SessionSearchUiState {

    data class Content(
            val sessions: List<SessionUiState>,
            val isLoadingVisible: Boolean,
            val titleId: Int,
            val subtitleId: Int
    ) : SessionSearchUiState

    object Error : SessionSearchUiState

}
