package nay.kirill.hideandseek.host.impl.presentation.hosting

sealed interface HostingEff {

    object RetryStartService : HostingEff

    object StopService : HostingEff

    data class ShowToast(val message: String) : HostingEff

}