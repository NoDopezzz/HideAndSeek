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

    private val clientManager: ClientManager = ClientManager(
            appContext = this,
            onMessage = {

            }
    )

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

        val device = intent.getParcelableExtra(BLUETOOTH_DEVICE_EXTRA, BluetoothDevice::class.java)
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
        clientManager.connect(device).useAutoConnect(true)?.enqueue()
    }

    private fun stopClientService() {
        clientManager.close()
    }

    companion object {

        const val BLUETOOTH_DEVICE_EXTRA = "BLUETOOTH_DEVICE_EXTRA"

    }
}
