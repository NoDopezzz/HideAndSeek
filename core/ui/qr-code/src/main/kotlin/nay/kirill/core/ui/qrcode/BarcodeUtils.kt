package nay.kirill.core.ui.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

object BarcodeUtils {

    fun createBarcodeBitmap(
            barcodeValue: String,
            widthPixels: Int,
            heightPixels: Int
    ): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(
                barcodeValue,
                BarcodeFormat.QR_CODE,
                widthPixels,
                heightPixels,
                mapOf(EncodeHintType.MARGIN to 0)
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(
                bitMatrix.width,
                bitMatrix.height,
                Bitmap.Config.ARGB_8888
        ).apply {
            setPixels(pixels, 0, bitMatrix.width, 0, 0, bitMatrix.width, bitMatrix.height)
        }

        return bitmap
    }

}
