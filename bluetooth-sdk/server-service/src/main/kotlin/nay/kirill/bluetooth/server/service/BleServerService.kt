package nay.kirill.bluetooth.server.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import nay.kirill.bluetooth.server.impl.ServerManager
import no.nordicsemi.android.ble.BleServerManager
import org.koin.android.ext.android.inject

/**
 * Foreground service that enables BLE server.
 * Before binding service make sure to declare it in Manifest
 */
class BleServerService : Service() {

    private val serverManager: ServerManager by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val notificationChannel = NotificationChannel(
                    BleServerService::class.java.simpleName,
                    resources.getString(R.string.server_service_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, BleServerService::class.java.simpleName)
                .setContentTitle(resources.getString(R.string.server_service_name))
                .setContentText(resources.getString(R.string.server_service_notification))
                .setAutoCancel(true)
                .build()

        startForeground(1, notification)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        if (bluetoothManager.adapter?.isEnabled == true) startServerService()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = BleServerBinderImpl()

    override fun onDestroy() {
        stopServerService()

        super.onDestroy()
    }

    private fun startServerService() {
        serverManager.open()
    }

    private fun stopServerService() {
        serverManager.close()
    }

    private inner class BleServerBinderImpl : ServiceBinder, Binder() {

        override fun sendMessage(message: String, deviceId: String?) {
            serverManager.sendMessage(message, deviceId)
        }

    }
}