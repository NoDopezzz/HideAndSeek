package nay.kirill.hideandseek.host.impl.presentation.foundinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import nay.kirill.bluetooth.server.service.BleServerService
import nay.kirill.core.arch.withArgs
import nay.kirill.core.arch.args
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FoundInfoFragment : Fragment() {

    private val viewModel: FoundInfoViewModel by viewModel()

    private val args: FoundInfoArgs by args()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                FoundInfoScreen(
                        type = args.type,
                        onButtonClick = ::back
                )
            }
        }
    }

    private fun back() {
        viewModel.back()
        stopServiceIfNeeded()
    }

    private fun stopServiceIfNeeded() {
        if (args.type == FoundType.ALL) {
            activity?.stopService(Intent(activity, BleServerService::class.java))
        }
    }

    companion object {

        fun newInstance(args: FoundInfoArgs) = FoundInfoFragment().withArgs(args)

    }

}
