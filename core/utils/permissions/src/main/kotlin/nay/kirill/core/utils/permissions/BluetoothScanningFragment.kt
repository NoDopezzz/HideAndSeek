package nay.kirill.core.utils.permissions

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import androidx.fragment.app.Fragment
import permissions.dispatcher.RuntimePermissions
import kotlin.jvm.functions.Function0

/**
 * Abstract Fragment for requesting permission for scanning bluetooth devices. In case your functionality requires
 * Bluetooth scanning be sure to inherit [BluetoothScanningFragment]
 *
 * You should use only [onCheckLocation] function of this class. Other functions are declared as public
 * for annotation processor purposes.
 */

@RuntimePermissions
abstract class BluetoothScanningFragment : Fragment() {

    /**
     * All required permissions can be gathered using [onCheckLocation]
     * @param block is lambda that is going to be invoked after all permissions will be granted
     */
    protected fun onCheckLocation(block: () -> Unit) {
        onCheckCoarseLocationWithPermissionCheck(object : Function0<Unit> {
            override fun invoke() = block()
        })
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onCheckCoarseLocation(block: Function0<Unit>) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> onCheckFineLocationWithPermissionCheck(block)
            else -> onCheckBluetoothScanWithPermissionCheck(block)
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onCheckFineLocation(block: Function0<Unit>) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> onCheckBluetoothScanWithPermissionCheck(block)
            else -> block.invoke()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @NeedsPermission(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun onCheckBluetoothScan(block: Function0<Unit>) {
        block.invoke()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForFineLocation(request: PermissionRequest) {
        showPermissionAlert(type = PermissionType.LOCATION, request = request)
    }

    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun showRationaleForCoarseLocation(request: PermissionRequest) {
        showPermissionAlert(type = PermissionType.LOCATION, request = request)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OnShowRationale(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun showRationaleForBluetooth(request: PermissionRequest) {
        showPermissionAlert(type = PermissionType.BLUETOOTH, request = request)
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onFineLocationPermissionNeverAskAgain() {
        showPermissionAlert(
                type = PermissionType.LOCATION,
                okAction = {
                    openPermissionSettings()
                },
                cancelAction = { /* Do nothing */ }
        )
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onCoarseLocationPermissionNeverAskAgain() {
        showPermissionAlert(
                type = PermissionType.LOCATION,
                okAction = {
                    openPermissionSettings()
                },
                cancelAction = { /* Do nothing */ }
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OnPermissionDenied(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun onBluetoothPermissionNeverAskAgain() {
        showPermissionAlert(
                type = PermissionType.BLUETOOTH,
                okAction = {
                    openPermissionSettings()
                },
                cancelAction = { /* Do nothing */ }
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun showPermissionAlert(type: PermissionType, request: PermissionRequest) {
        showPermissionAlert(type, okAction = request::proceed, cancelAction = request::cancel)
    }

    private fun showPermissionAlert(
            type: PermissionType,
            okAction: () -> Unit,
            cancelAction: () -> Unit
    ) {
        AlertDialog.Builder(requireContext())
                .setTitle(type.titleId)
                .setMessage(type.subtitleId)
                .setPositiveButton(R.string.positive_button_text) { _, _ -> okAction() }
                .setNegativeButton(R.string.negative_button_text) { _, _ -> cancelAction() }
                .show()
    }

    private fun openPermissionSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", activity?.packageName, null)
        }
        requireContext().startActivity(intent)
    }

}