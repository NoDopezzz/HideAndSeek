package nay.kirill.hideandseek.host.impl.presentation.seek

import androidx.annotation.StringRes

sealed interface SeekState {

    object Error : SeekState

    object NoDevicesConnected : SeekState

    data class Content(
            val locations: List<String>
    ) : SeekState

}

sealed interface SeekUiState {

    data class Error(
            @StringRes val descriptionId: Int
    ) : SeekUiState

    data class Content(
            val devicesLeft: Int,
            val locations: List<String>
    ) : SeekUiState

}
