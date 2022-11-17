package nay.kirill.hideandseek.host.impl.presentation.seek

import androidx.annotation.StringRes
import nay.kirill.core.ui.radar.RadarLocation

sealed interface SeekState {

    object Error : SeekState

    object NoDevicesConnected : SeekState

    data class Content(
            val locations: Map<String, RadarLocation>,
            val currentLocation: RadarLocation? = null
    ) : SeekState

}

sealed interface SeekUiState {

    data class Error(
            @StringRes val descriptionId: Int
    ) : SeekUiState

    data class Content(
            val devicesLeft: Int,
            val currentLocation: RadarLocation?,
            val locations: List<RadarLocation>
    ) : SeekUiState

}
