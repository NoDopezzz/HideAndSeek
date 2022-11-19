package nay.kirill.core.ui.qrcode

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@Composable
fun QrScanView(
        modifier: Modifier = Modifier,
        onScan: (String) -> Unit
) {
    val context = LocalContext.current
    var scanFlag by remember {
        mutableStateOf(false)
    }

    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {
            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("")
            capture.decode()
            this.decodeContinuous { result ->
                if(scanFlag){
                    return@decodeContinuous
                }
                scanFlag = true
                result.text?.let { value->
                    onScan(value)
                    scanFlag = false
                }
            }
        }
    }

    DisposableEffect(key1 = "compoundBarcodeView" ){
        compoundBarcodeView.resume()
        onDispose {
            compoundBarcodeView.pause()
        }
    }

    AndroidView(
            modifier = modifier,
            factory = { compoundBarcodeView },
    )
}
