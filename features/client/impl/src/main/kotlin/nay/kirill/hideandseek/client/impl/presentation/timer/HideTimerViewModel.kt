package nay.kirill.hideandseek.client.impl.presentation.timer

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.hideandseek.client.impl.presentation.ClientNavigation

internal class HideTimerViewModel(
        converter: HideTimerConverter,
        private val navigation: ClientNavigation,
        clientEventCallback: ClientEventCallback
) : BaseEffectViewModel<HideCounterState, HideCounterUiState, HideTimerEffect>(
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
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            for (i in 10 downTo 0) {
                delay(1000)
                withContext(Dispatchers.Main) { state = HideCounterState.Content(i) }
            }
            withContext(Dispatchers.Main) {
                navigation.openHide()
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
                _effect.trySend(HideTimerEffect.Error)
            }
            else -> Unit
        }
    }

}
