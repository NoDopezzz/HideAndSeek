plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
        target = project,
        targetPackage = "nay.kirill.hideandseek.impl",
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
}