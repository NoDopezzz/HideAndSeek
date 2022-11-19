package nay.kirill.hideandseek.client.impl.presentation.hide

sealed interface HideEffect {

    object StopService : HideEffect

    object StartSound : HideEffect

    object StopSound : HideEffect

}
