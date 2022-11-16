package nay.kirill.hideandseek.hosting.impl.presentation.timer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.server.service.BleServerService
import nay.kirill.hideandseek.hosting.impl.presentation.hosting.HostingEff
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SeekTimerFragment : Fragment() {

    private val viewModel: SeekTimerViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                SeekTimerScreen(
                        state = viewModel.uiState.value,
                        onBack = ::back,
                        onRetry = ::retry
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startTimer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeEffects()

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })
    }

    private fun back() {
        viewModel.back()
        stopService()
    }

    private fun retry() {
        viewModel.retry()
        stopService()
    }

    private fun stopService() {
        activity?.stopService(Intent(activity, BleServerService::class.java))
    }

    private fun observeEffects() {
        viewModel.effect
                .flowWithLifecycle(lifecycle)
                .onEach(::onEffect)
                .launchIn(lifecycleScope)
    }

    private fun onEffect(eff: SeekTimerEffect) {
        when (eff) {
            is SeekTimerEffect.Error -> stopService()
        }
    }

    companion object {

        fun newInstance() = SeekTimerFragment()

    }

}
