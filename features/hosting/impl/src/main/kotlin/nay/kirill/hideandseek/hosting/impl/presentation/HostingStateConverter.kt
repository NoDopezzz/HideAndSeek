package nay.kirill.hideandseek.hosting.impl.presentation

internal class HostingStateConverter : (HostingState) -> HostingUiState {

    override fun invoke(state: HostingState): HostingUiState = HostingUiState()
}