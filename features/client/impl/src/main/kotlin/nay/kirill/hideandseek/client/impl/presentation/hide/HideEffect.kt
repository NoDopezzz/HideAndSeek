package nay.kirill.hideandseek.client.impl.presentation.hide

sealed interface HideEffect {

    object StopService : HideEffect

}
