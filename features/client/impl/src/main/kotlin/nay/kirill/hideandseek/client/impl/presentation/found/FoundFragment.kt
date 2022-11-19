package nay.kirill.hideandseek.client.impl.presentation.found

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FoundFragment : Fragment() {

    private val viewModel: FoundViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                FoundScreen(
                        onBack = viewModel::back
                )
            }
        }
    }

    companion object {

        fun newInstance() = FoundFragment()

    }
}