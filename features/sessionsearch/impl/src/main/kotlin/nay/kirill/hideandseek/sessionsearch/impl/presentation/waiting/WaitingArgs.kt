package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class WaitingArgs(
        val bluetoothDevice: BluetoothDevice
): Parcelable