package nay.kirill.hideandseek.sessionsearch.impl.presentation.waiting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import nay.kirill.bluetooth.client.service.BleClientService
import nay.kirill.core.arch.withArgs
import nay.kirill.core.arch.args
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class WaitingFragment : Fragment() {

    private val args: WaitingArgs by args()

    private val viewModel: WaitingViewModel by viewModel { parametersOf(args) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                WaitingScreen(
                        state = viewModel.uiState.value,
                        onBack = ::back
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })
    }

    private fun back() {
        activity?.stopService(Intent(activity, BleClientService::class.java))
        viewModel.back()
    }

    companion object {

        fun newInstance(args: WaitingArgs) = WaitingFragment().withArgs(args)

    }

}