package nay.kirill.hideandseek.hosting.impl.presentation

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.hosting.impl.presentation.models.ButtonAction

internal class HostingViewModel(
        converter: HostingStateConverter,
        private val navigation: HostingNavigation
) : BaseViewModel<HostingState, HostingUiState>(
        converter = converter,
        initialState = HostingState(
                connectedDeviceEvent = ContentEvent.Loading(),
                hostDeviceName = ""
        )
) {

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun init(manager: BluetoothManager) {
        state = state.copy(hostDeviceName = manager.adapter.name)
    }

    fun onButtonClicked(buttonAction: ButtonAction) {
        when (buttonAction) {
            is ButtonAction.Start -> TODO()
            is ButtonAction.Retry -> TODO()
            is ButtonAction.Back -> navigation.back()
        }
    }

}
