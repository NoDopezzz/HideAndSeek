package nay.kirill.hideandseek.host.impl.presentation.timer

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nay.kirill.bluetooth.server.callback.event.ServerEvent
import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.hideandseek.host.impl.presentation.HostNavigation

internal class SeekTimerViewModel(
        converter: SeekTimerConverter,
        serverEventCallback: ServerEventCallback,
        private val navigation: HostNavigation
) : BaseEffectViewModel<SeekTimerState, SeekTimerUiState, SeekTimerEffect>(
        converter = converter,
        initialState = SeekTimerState.Content(count = 60)
) {

    private var timerJob: Job? = null

    init {
        serverEventCallback.result
                .onEach { event ->
                    when {
                        event is ServerEvent.OnFatalException -> {
                            state = SeekTimerState.Error
                            timerJob?.cancel()
                            _effect.trySend(SeekTimerEffect.Error)
                        }
                        event is ServerEvent.OnDeviceDisconnected && event.deviceCount == 0 -> {
                            state = SeekTimerState.NoDevicesConnected
                            timerJob?.cancel()
                            _effect.trySend(SeekTimerEffect.Error)
                        }
                    }
                }
                .launchIn(viewModelScope)
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            for (i in 59 downTo 0) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    state = SeekTimerState.Content(i)
                }
            }
            navigation.openSeek()
        }
    }

    fun back() = navigation.back()

    fun retry() = navigation.openHosting()

}