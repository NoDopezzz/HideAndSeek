package nay.kirill.hideandseek.hosting.impl.presentation

sealed interface HostingEff {

    object RetryStartService : HostingEff

}