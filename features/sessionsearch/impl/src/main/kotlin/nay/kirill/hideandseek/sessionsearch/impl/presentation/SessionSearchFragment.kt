package nay.kirill.hideandseek.sessionsearch.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SessionSearchFragment : Fragment() {

    private val viewModel: SessionSearchViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                SessionSearchScreen(
                        state = viewModel.uiState.value,
                        onBack = viewModel::back,
                        onRetry = {  }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDevices()
    }

    companion object {

        fun newInstance() = SessionSearchFragment()

    }
}
