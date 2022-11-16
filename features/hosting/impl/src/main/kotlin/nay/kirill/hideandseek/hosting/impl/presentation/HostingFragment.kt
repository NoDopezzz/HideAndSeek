package nay.kirill.hideandseek.hosting.impl.presentation

import android.bluetooth.BluetoothManager
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.server.service.BleServerService
import nay.kirill.core.utils.permissions.PermissionsUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HostingFragment : Fragment() {

    private val viewModel: HostingViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                HostingScreen(
                        state = viewModel.uiState.value,
                        onButtonClicked = viewModel::onButtonClicked,
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (PermissionsUtils.checkBluetoothConnectPermission(requireContext())) {
            viewModel.init(requireContext().getSystemService(BLUETOOTH_SERVICE) as BluetoothManager)
        }

        startService()

        observeEffects()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.stopService(Intent(activity, BleServerService::class.java))
    }

    private fun startService() {
        activity?.startService(Intent(activity, BleServerService::class.java))
    }

    private fun observeEffects() {
        viewModel.effect
                .flowWithLifecycle(lifecycle)
                .onEach(::onEffect)
                .launchIn(lifecycleScope)
    }

    private fun onEffect(eff: HostingEff) {
        when (eff) {
            is HostingEff.RetryStartService -> startService()
            is HostingEff.ShowToast -> showToast(eff.message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance() = HostingFragment()

    }

}
