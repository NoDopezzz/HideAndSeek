package nay.kirill.hideandseek.hosting.impl.presentation

sealed interface HostingEff {

    object RetryStartService : HostingEff

    data class ShowToast(val message: String) : HostingEff

}