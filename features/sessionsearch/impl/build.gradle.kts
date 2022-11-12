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

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.foundation)
    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)
}