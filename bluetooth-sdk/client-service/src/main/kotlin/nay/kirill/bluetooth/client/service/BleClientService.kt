package nay.kirill.bluetooth.client.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import nay.kirill.bluetooth.client.ClientManager

class BleClientService : Service() {

    private var clientManager: ClientManager? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val notificationChannel = NotificationChannel(
                    BleClientService::class.java.simpleName,
                    resources.getString(R.string.client_service_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, BleClientService::class.java.simpleName)
                .setContentTitle(resources.getString(R.string.client_service_name))
                .setContentText(resources.getString(R.string.client_service_notification))
                .setAutoCancel(true)
                .build()

        startForeground(1, notification)

        val device = intent.getParcelableExtra<BluetoothDevice>(BLUETOOTH_DEVICE_EXTRA)
        if (device != null) startClientService(device)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopClientService()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun startClientService(device: BluetoothDevice) {
        clientManager = ClientManager(this)
        clientManager
                ?.connect(device)
                ?.retry(3, 100)
                ?.timeout(15_000)
                ?.useAutoConnect(false)
                ?.enqueue()
    }

    private fun stopClientService() {
        clientManager?.close()
        clientManager = null
    }

    companion object {

        const val BLUETOOTH_DEVICE_EXTRA = "BLUETOOTH_DEVICE_EXTRA"

    }
}
