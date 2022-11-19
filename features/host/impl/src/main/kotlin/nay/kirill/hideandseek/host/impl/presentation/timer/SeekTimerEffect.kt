package nay.kirill.hideandseek.host.impl.presentation.timer

sealed interface SeekTimerEffect {

    object Error : SeekTimerEffect

}