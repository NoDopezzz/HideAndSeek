package nay.kirill.hideandseek.mainmenu.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.fragment.app.Fragment

internal class MainMenuFragment : Fragment() {

    private val viewModel: MainMenuViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                MainMenuScreen(
                    onCreateSession = viewModel::onCreateSession,
                    onConnect = viewModel::onConnectToSession
                )
            }
        }
    }

    companion object {

        fun newInstance() = MainMenuFragment()

    }
}
