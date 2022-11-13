import org.gradle.kotlin.dsl.DependencyHandlerScope

object Libraries {

    object Androidx {

        const val core = "androidx.core:core-ktx:1.7.0"

        const val appCompat = "androidx.appcompat:appcompat:1.5.1"

        const val constraint = "androidx.constraintlayout:constraintlayout:2.1.4"

    }

    object Google {

        const val material = "com.google.android.material:material:1.6.1"

    }

    object Koin {

        private const val version = "3.3.0"

        const val koinAndroid = "io.insert-koin:koin-android:$version"

        const val compose = "io.insert-koin:koin-androidx-compose:$version"

    }

    object Navigation {

        const val cicerone = "com.github.terrakok:cicerone:7.1"

    }

    object Lifecycle {

        private const val version = "2.2.0"

        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"

        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"

    }

    object Compose {

        private const val version = "1.0.5"

        const val material = "androidx.compose.material:material"

        const val preview = "androidx.compose.ui:ui-tooling-preview"

        const val debugPreview = "androidx.compose.ui:ui-tooling"

        const val runtime = "androidx.compose.runtime:runtime:$version"

        const val foundation = "androidx.compose.foundation:foundation"

        const val bom = "androidx.compose:compose-bom:2022.10.00"

    }

    object Bluetooth {

        private const val version = "2.5.1"

        const val nordic = "no.nordicsemi.android:ble-ktx:$version"

        const val nordicCommon = "no.nordicsemi.android:ble-common:$version"

        const val nordicExtensions = "no.nordicsemi.android:ble-ktx:$version"

        const val scanner = "no.nordicsemi.android.support.v18:scanner:1.6.0"

    }

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

}
