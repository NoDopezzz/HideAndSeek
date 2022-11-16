plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
        target = project,
        targetPackage = "nay.kirill.hideandseek.sessionsearch.api"
    )
}

dependencies {
    implementation(Libraries.Navigation.cicerone)
}