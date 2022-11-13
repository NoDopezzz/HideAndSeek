package nay.kirill.hideandseek.mainmenu.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import org.koin.androidx.viewmodel.ext.android.viewModel
import nay.kirill.core.utils.permissions.BluetoothScanningFragment

class MainMenuFragment : BluetoothScanningFragment() {

    private val viewModel: MainMenuViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                MainMenuScreen(
                        onCreateSession = {
                            onCheckLocation {
                                viewModel.onCreateSession()
                            }
                        },
                        onConnect = {
                            onCheckLocation {
                                viewModel.onConnectToSession()
                            }
                        }
                )
            }
        }
    }

    companion object {

        fun newInstance() = MainMenuFragment()

    }

}
