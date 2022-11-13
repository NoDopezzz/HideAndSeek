package nay.kirill.hideandseek.mainmenu.impl.presentation

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.fragment.app.Fragment
import nay.kirill.core.utils.permissions.PermissionsUtils
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainMenuFragment : Fragment() {

    private val viewModel: MainMenuViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                MainMenuScreen(
                        onCreateSession = viewModel::onCreateSession,
                        onConnect = ::onCheckCoarseLocationWithPermissionCheck
                )
            }
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onCheckCoarseLocation() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> onCheckFineLocationWithPermissionCheck()
            else -> onCheckBluetoothAdminWithPermissionCheck()
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onCheckFineLocation() {
        when {
            Build.VERSION.SDK_INT <= Build.VERSION_CODES.R -> onCheckBluetoothAdminWithPermissionCheck()
            else -> onCheckBluetoothScanWithPermissionCheck()
        }
    }

    @NeedsPermission(Manifest.permission.BLUETOOTH_ADMIN)
    fun onCheckBluetoothAdmin() {
        when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.S -> onCheckBluetoothScanWithPermissionCheck()
            else -> viewModel.onConnectToSession()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @NeedsPermission(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun onCheckBluetoothScan() {
        viewModel.onConnectToSession()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForLocation(request: PermissionRequest) {
        AlertDialog.Builder(requireContext())
                .setTitle("Предоставьте разрешение на геолокацию")
                .setMessage("Разрешение геолокации нужно для использования Bluetooth. Приложение не собирает никакой конфиденциальной информации, связанной с местоположением пользователей")
                .setPositiveButton("Ок") { _, _ -> request.proceed() }
                .setNegativeButton("Отмена") { _, _ -> request.cancel() }
                .show()
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationPermissionDenied() {
        Toast.makeText(requireContext(), "Отклонено", Toast.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationPermissionNeverAskAgain() {
        Toast.makeText(requireContext(), "Запрещено", Toast.LENGTH_LONG).show()
    }

    companion object {

        fun newInstance() = MainMenuFragment()

    }
}
