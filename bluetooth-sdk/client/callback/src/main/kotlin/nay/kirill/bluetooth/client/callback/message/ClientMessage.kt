package nay.kirill.bluetooth.client.callback.message

sealed interface ClientMessage {

    data class SendMessage(val message: String) : ClientMessage

}
