package nay.kirill.hideandseek.di

import nay.kirill.hideandseek.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val mainModule = module {
    viewModelOf(::MainViewModel)
}