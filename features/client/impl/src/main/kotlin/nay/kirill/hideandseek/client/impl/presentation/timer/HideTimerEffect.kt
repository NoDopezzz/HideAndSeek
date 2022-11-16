package nay.kirill.hideandseek.client.impl.presentation.timer

sealed interface HideTimerEffect {

    object Error : HideTimerEffect

}