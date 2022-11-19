package nay.kirill.hideandseek.host.impl.presentation.timer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class SeekTimerArgs(
        val connectedDevicesCount: Int
) : Parcelable