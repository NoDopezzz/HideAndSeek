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
import nay.kirill.bluetooth.client.ClientConsumerCallback
import nay.kirill.bluetooth.client.ClientManager
import nay.kirill.bluetooth.client.callback.ClientEvent
import nay.kirill.bluetooth.client.callback.ClientServiceCallback
import no.nordicsemi.android.ble.PhyRequest
import org.koin.android.ext.android.inject

class BleClientService : Service() {

    private val clientServiceCallback : ClientServiceCallback by inject()

    private var clientManager: ClientManager? = null

    private val consumerCallback = object : ClientConsumerCallback {

        override fun onServiceInvalidated() {
            clientServiceCallback.setResult(ClientEvent.ServiceInvalidated)
        }

        override fun onNewMessage(device: BluetoothDevice, message: String) {
            clientServiceCallback.setResult(ClientEvent.OnNewMessage(message))
        }

        override fun onSubscriptionSuccess(device: BluetoothDevice) {
            clientServiceCallback.setResult(ClientEvent.SubscriptionResult(result = Result.success(device)))
        }

        override fun onSubscriptionFailed(error: Throwable) {
            clientServiceCallback.setResult(ClientEvent.SubscriptionResult(result = Result.failure(error)))
        }

    }

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
        clientManager = ClientManager(this, consumerCallback)
        clientManager
                ?.connect(device)
                ?.retry(4, 150)
                ?.useAutoConnect(false)
                ?.done {
                    clientServiceCallback.setResult(ClientEvent.ConnectionResult(Result.success(it)))
                }
                ?.fail { _, status ->
                    clientServiceCallback.setResult(
                            value = ClientEvent.ConnectionResult(
                                    connectResult = Result.failure(IllegalStateException("Failed to connect to bluetooth device: $status"))
                            )
                    )
                }
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
