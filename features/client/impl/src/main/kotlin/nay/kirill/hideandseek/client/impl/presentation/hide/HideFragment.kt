package nay.kirill.hideandseek.client.impl.presentation.hide

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.client.service.BleClientService
import nay.kirill.core.utils.permissions.PermissionsUtils
import nay.kirill.hideandseek.client.impl.R
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HideFragment : Fragment() {

    private val viewModel: HideViewModel by viewModel()

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            HideScreen(
                    state = viewModel.uiState.value,
                    onRetry = ::retry,
                    onBack = ::back
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sound)
        mediaPlayer?.isLooping = true

        if (PermissionsUtils.checkFineLocation(requireContext())) {
            viewModel.startLocationUpdating()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })

        viewModel.effect
                .flowWithLifecycle(lifecycle)
                .onEach { eff ->
                    when (eff) {
                        is HideEffect.StopService -> stopService()
                        is HideEffect.StopSound -> mediaPlayer?.pause()
                        is HideEffect.StartSound -> mediaPlayer?.start()
                    }
                }
                .launchIn(lifecycleScope)
    }

    private fun retry() {
        stopService()
        viewModel.retry()
    }

    private fun back() {
        stopService()
        viewModel.back()
    }

    private fun stopService() {
        activity?.stopService(Intent(activity, BleClientService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer = null
    }

    companion object {

        fun newInstance() = HideFragment()

    }

}
