plugins {
    kotlin("android")
    id("com.android.library")
    id("kotlin-parcelize")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.hideandseek.host.impl",
            compose = true
    )
}

dependencies {
    implementation(Libraries.Koin.koinAndroid)
    implementation(Libraries.Navigation.cicerone)

    implementation(project(Project.Features.hostApi))

    implementation(project(Project.Core.arch))
    implementation(project(Project.Core.UI.button))
    implementation(project(Project.Core.UI.compose))
    implementation(project(Project.Core.UI.topbar))
    implementation(project(Project.Core.UI.res))
    implementation(project(Project.Core.UI.list))
    implementation(project(Project.Core.UI.error))
    implementation(project(Project.Core.UI.timer))
    implementation(project(Project.Core.UI.radar))
    implementation(project(Project.Core.UI.qrCode))

    implementation(project(Project.Core.Utils.permissions))
    implementation(project(Project.Core.Utils.callbackFlow))

    implementation(project(Project.BluetoothSDK.scannerApi))
    implementation(project(Project.BluetoothSDK.serverService))
    implementation(project(Project.BluetoothSDK.serverCallback))
    implementation(project(Project.BluetoothSDK.serverExceptions))
    implementation(project(Project.BluetoothSDK.messages))

    implementation(project(Project.LocationSDK.api))

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.foundation)
    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(Libraries.coroutines)
}