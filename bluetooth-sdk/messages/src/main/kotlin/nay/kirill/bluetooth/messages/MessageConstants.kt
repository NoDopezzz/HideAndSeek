package nay.kirill.bluetooth.messages

sealed class Message {

    object Start : Message()

    data class Location(
            val latitude: Double,
            val longitude: Double
    ) : Message()

    data class IsNear(
            val isNear: Boolean
    ) : Message()

    object Unknown : Message()

    companion object {

        fun toByteArray(message: Message) = when (message) {
            is Start -> "START".toByteArray()
            is Location -> "${message.latitude};${message.longitude}".toByteArray()
            is IsNear -> "IsNear:${message.isNear}".toByteArray()
            is Unknown -> ByteArray(0)
        }

        fun fromByteArray(byteArray: ByteArray): Message {
            val value = String(byteArray)
            return try {
                when {
                    value == "START" -> Start
                    ".*;.*".toRegex().matches(value) -> Location(
                            latitude = value.split(";")[0].toDouble(),
                            longitude = value.split(";")[1].toDouble()
                    )
                    value.contains("IsNear:") -> IsNear(
                            value.split("IsNear:")[1].toBoolean()
                    )
                    else -> Unknown
                }
            } catch (e: Throwable) {
                Unknown
            }
        }

    }

}
