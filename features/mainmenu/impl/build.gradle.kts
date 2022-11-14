plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
}

android {
    libraryConfig(
        target = project,
        targetPackage = "nay.kirill.hideandseek.mainmenu.impl",
        compose = true
    )
}

dependencies {
    implementation(Libraries.Androidx.constraint)
    implementation(Libraries.Androidx.appCompat)
    implementation(Libraries.Navigation.cicerone)
    implementation(Libraries.Koin.koinAndroid)

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.foundation)
    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(project(Project.Features.mainApi))
    implementation(project(Project.Core.UI.compose))
    implementation(project(Project.Core.UI.button))
    implementation(project(Project.Core.UI.topbar))
    implementation(project(Project.Core.Utils.permissions))

    implementation(project(Project.Features.sessionSearchApi))
    implementation(project(Project.Features.hostingApi))

    implementation(Libraries.Permission.permissionDispatcher)
    kapt(Libraries.Permission.permissionDispatcherProcessor)
}