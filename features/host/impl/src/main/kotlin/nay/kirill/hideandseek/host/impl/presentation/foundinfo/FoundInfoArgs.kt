package nay.kirill.hideandseek.host.impl.presentation.foundinfo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class FoundInfoArgs(
        val type: FoundType
) : Parcelable

enum class FoundType {
    ALL, ONE
}
