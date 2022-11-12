plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
        target = project,
        targetPackage = "nay.kirill.hideandseek.sessionsearch.impl",
        compose = true
    )
}

dependencies {
    implementation(Libraries.Koin.koinAndroid)
    implementation(Libraries.Navigation.cicerone)

    implementation(project(Project.Features.sessionSearchApi))

    implementation(project(Project.Core.arch))
    implementation(project(Project.Core.UI.button))
    implementation(project(Project.Core.UI.compose))
    implementation(project(Project.Core.UI.topbar))

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.foundation)
    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(Libraries.coroutines)
}