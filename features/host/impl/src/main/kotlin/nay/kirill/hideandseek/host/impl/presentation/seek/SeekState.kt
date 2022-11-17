package nay.kirill.hideandseek.host.impl.presentation.seek

import androidx.annotation.StringRes
import nay.kirill.hideandseek.host.impl.presentation.seek.models.Location

sealed interface SeekState {

    object Error : SeekState

    object NoDevicesConnected : SeekState

    data class Content(
            val locations: Map<String, Location>
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
