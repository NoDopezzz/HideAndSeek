package nay.kirill.hideandseek.sessionsearch.impl.presentation.counter

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
import nay.kirill.bluetooth.client.service.BleClientService
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HideCounterFragment : Fragment() {

    private val viewModel: HideCounterViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                HideCounterScreen(
                        state = viewModel.uiState.value,
                        onBack = ::back,
                        onRetry = ::retry
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })

        viewModel.effect
                .flowWithLifecycle(lifecycle)
                .onEach { eff ->
                    when (eff) {
                        is HideCounterEffect.Error -> activity?.stopService(Intent(activity, BleClientService::class.java))
                    }
                }
                .launchIn(lifecycleScope)
    }

    private fun back() {
        activity?.stopService(Intent(activity, BleClientService::class.java))
        viewModel.back()
    }

    private fun retry() {
        activity?.stopService(Intent(activity, BleClientService::class.java))
        viewModel.retry()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startTimer()
    }

    companion object {

        fun newInstance() = HideCounterFragment()

    }

}
