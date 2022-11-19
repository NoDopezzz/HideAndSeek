package nay.kirill.hideandseek.host.impl.presentation.seek

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class SeekArgs(
        val connectedDeviceCount: Int
) : Parcelable