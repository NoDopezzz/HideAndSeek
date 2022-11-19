package nay.kirill.hideandseek.host.impl.presentation.foundinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import nay.kirill.core.arch.withArgs
import nay.kirill.core.arch.args
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FoundInfoFragment : Fragment() {

    private val viewModel: FoundInfoViewModel by viewModel()

    private val args: FoundInfoArgs by args()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                FoundInfoScreen(
                        type = args.type,
                        onButtonClick = viewModel::onButtonClick
                )
            }
        }
    }

    companion object {

        fun newInstance(args: FoundInfoArgs) = FoundInfoFragment().withArgs(args)

    }

}
