package nay.kirill.bluetooth.server.callback

import nay.kirill.core.utils.callbackFlow.CallbackFlow

/**
 * Callback for events from BLE-server service.
 */
class ServerServiceCallback : CallbackFlow<ServerEvent>()