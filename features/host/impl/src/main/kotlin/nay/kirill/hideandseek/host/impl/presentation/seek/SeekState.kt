package nay.kirill.hideandseek.host.impl.presentation.seek

import android.location.Location
import androidx.annotation.StringRes
import nay.kirill.core.ui.radar.RadarLocation

sealed interface SeekState {

    object Error : SeekState

    object NoDevicesConnected : SeekState

    data class Content(
            val locations: Map<String, RadarLocation>,
            val leftDevicesCount: Int,
            val currentLocation: Location? = null,
            val isScanning: Boolean = false
    ) : SeekState

}

sealed interface SeekUiState {

    data class Error(
            @StringRes val descriptionId: Int
    ) : SeekUiState

    data class Content(
            val devicesLeft: Int,
            val currentLocation: Location?,
            val locations: List<RadarLocation>,
            val showButton: Boolean = false,
            val isScanningView: Boolean = false
    ) : SeekUiState

}
