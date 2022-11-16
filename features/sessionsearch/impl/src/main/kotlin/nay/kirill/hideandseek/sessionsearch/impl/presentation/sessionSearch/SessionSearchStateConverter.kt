package nay.kirill.hideandseek.sessionsearch.impl.presentation.sessionSearch

import android.annotation.SuppressLint
import android.content.Context
import nay.kirill.core.arch.ContentEvent
import nay.kirill.hideandseek.sessionsearch.impl.R
import nay.kirill.hideandseek.sessionsearch.impl.presentation.views.SessionUiState
import nay.kirill.core.utils.permissions.PermissionsUtils

internal class SessionSearchStateConverter(
        private val context: Context
) : (SessionSearchState) -> SessionSearchUiState {

    @SuppressLint("MissingPermission") // Permissions handled in PermissionsUtils
    override fun invoke(state: SessionSearchState): SessionSearchUiState = when {
        !PermissionsUtils.checkScanningPermissions(context) -> SessionSearchUiState.Error
        state.devicesEvent is ContentEvent.Error -> SessionSearchUiState.Error
        else -> SessionSearchUiState.Content(
                titleId = R.string.session_search_title,
                subtitleId = R.string.session_search_subtitle,
                isLoadingVisible = state.deviceAddressToConnect == null,
                sessions = state.devicesEvent.data
                        ?.map {
                            SessionUiState(
                                    deviceAddress = it.address,
                                    name = it.name ?: it.address,
                                    isLoading = it.address == state.deviceAddressToConnect
                            )
                        }
                        .orEmpty()
        )
    }
}