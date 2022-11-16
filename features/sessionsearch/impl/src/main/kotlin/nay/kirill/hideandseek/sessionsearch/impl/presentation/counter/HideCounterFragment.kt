package nay.kirill.hideandseek.sessionsearch.impl.presentation.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HideCounterFragment : Fragment() {

    private val viewModel: HideCounterViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                HideCounterScreen(
                        state = viewModel.uiState.value,
                        onBack = viewModel::back,
                        onRetry = viewModel::retry
                )
            }
        }
    }

    companion object {

        fun newInstance() = HideCounterFragment()

    }

}
