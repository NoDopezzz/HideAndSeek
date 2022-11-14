package nay.kirill.hideandseek.hosting.impl.presentation

import android.bluetooth.BluetoothManager
import android.content.ComponentName
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import nay.kirill.bluetooth.server.service.BleServerService
import nay.kirill.bluetooth.server.service.ServiceBinder
import nay.kirill.core.utils.permissions.PermissionsUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HostingFragment : Fragment() {

    private val viewModel: HostingViewModel by viewModel()

    private var serviceBinder: ServiceBinder? = null

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

        activity?.startService(Intent(activity, BleServerService::class.java))

        activity?.bindService(Intent(activity, BleServerService::class.java), connection, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unbindService(connection)

        activity?.stopService(Intent(activity, BleServerService::class.java))
    }

    companion object {

        fun newInstance() = HostingFragment()

    }

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            serviceBinder = binder as? ServiceBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBinder = null
        }

    }

}
