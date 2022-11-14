package nay.kirill.hideandseek.hosting.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HostingFragment : Fragment() {

    private val viewModel: HostingViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                HostingScreen(
                        state = viewModel.uiState.value,
                        onButtonClicked = viewModel::onButtonClicked,
                )
            }
        }
    }

    companion object {

        fun newInstance() = HostingFragment()

    }

}
