package nay.kirill.hideandseek.host.impl.presentation.seek

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.server.service.BleServerService
import nay.kirill.core.arch.args
import nay.kirill.core.arch.withArgs
import nay.kirill.core.utils.permissions.PermissionsUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class SeekFragment : Fragment() {

    private val viewModel: SeekViewModel by viewModel {
        parametersOf(args)
    }

    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = requireContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            requireContext().getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
    }

    private val args: SeekArgs by args()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            SeekScreen(
                    state = viewModel.uiState.value,
                    onBack = ::back,
                    onRetry = ::retry,
                    onPhoto = viewModel::onPhoto,
                    onLocation = viewModel::onLocation,
                    onScan = viewModel::onScan
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeEffects()

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })

        if (PermissionsUtils.checkFineLocation(requireContext())) {
            viewModel.fetchCurrentLocation()
        }
    }

    private fun observeEffects() {
        viewModel.effect
                .flowWithLifecycle(lifecycle)
                .onEach(::onEffect)
                .launchIn(lifecycleScope)
    }

    private fun onEffect(eff: SeekEffect) {
        when (eff) {
            is SeekEffect.StopService -> stopService()
            is SeekEffect.ShowToast -> Toast.makeText(context, eff.message, Toast.LENGTH_SHORT).show()
            is SeekEffect.Vibrate -> vibrate()
        }
    }

    private fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
    }

    private fun back() {
        stopService()
        viewModel.back()
    }

    private fun retry() {
        stopService()
        viewModel.retry()
    }

    private fun stopService() {
        activity?.stopService(Intent(activity, BleServerService::class.java))
    }

    companion object {

        fun newInstance(args: SeekArgs) = SeekFragment().withArgs(args)

    }

}
