package nay.kirill.hideandseek.hosting.impl.presentation.timer

sealed interface SeekTimerEffect {

    object Error : SeekTimerEffect

}