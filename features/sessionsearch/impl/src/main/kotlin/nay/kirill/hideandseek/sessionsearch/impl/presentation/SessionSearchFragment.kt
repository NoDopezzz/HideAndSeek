package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.client.service.BleClientService
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SessionSearchFragment : Fragment() {

    private val viewModel: SessionSearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

        lifecycleScope.launch {
            viewModel.effect
                    .flowWithLifecycle(lifecycle)
                    .onEach(::onEffect)
                    .launchIn(lifecycleScope)
        }
    }

    private fun onEffect(effect: HostingEffect) {
        when (effect) {
            is HostingEffect.StartService -> startService(device = effect.device)
        }
    }

    private fun startService(device: BluetoothDevice) {
        val intent = Intent(activity, BleClientService::class.java).run {
            putExtra(BleClientService.BLUETOOTH_DEVICE_EXTRA, device)
        }
        activity?.startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        activity?.stopService(Intent(activity, BleClientService::class.java))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                SessionSearchScreen(
                        state = viewModel.uiState.value,
                        onBack = viewModel::back,
                        onRetry = viewModel::getDevices,
                        onConnectToDevice = viewModel::onConnect
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDevices()
    }

    companion object {

        fun newInstance() = SessionSearchFragment()

    }
}
