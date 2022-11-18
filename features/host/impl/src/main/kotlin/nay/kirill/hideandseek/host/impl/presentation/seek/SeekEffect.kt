package nay.kirill.hideandseek.host.impl.presentation.seek

sealed interface SeekEffect {

    object StopService : SeekEffect

    data class ShowToast(val message: String) : SeekEffect

    object Vibrate : SeekEffect

}
