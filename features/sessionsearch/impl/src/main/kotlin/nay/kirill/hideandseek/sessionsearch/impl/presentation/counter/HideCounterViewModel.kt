package nay.kirill.hideandseek.sessionsearch.impl.presentation.counter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.hideandseek.sessionsearch.impl.presentation.Navigation

internal class HideCounterViewModel(
        converter: HideCounterConverter,
        private val navigation: Navigation,
        clientEventCallback: ClientEventCallback
) : BaseEffectViewModel<HideCounterState, HideCounterUiState, HideCounterEffect>(
        converter = converter,
        initialState = HideCounterState.Content(count = 60)
) {

    private var timerJob: Job? = null

    init {
        clientEventCallback.result
                .onEach(::handleEvent)
                .launchIn(viewModelScope)
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (i in 59 downTo 0) {
                delay(1000)
                state = HideCounterState.Content(i)
            }
        }
    }

    fun back() = navigation.back()

    fun retry() = navigation.openSessionSearching()

    private fun handleEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.OnFailure -> {
                timerJob?.cancel()
                state = HideCounterState.Error
                _effect.trySend(HideCounterEffect.Error)
            }
            else -> Unit
        }
    }

}
