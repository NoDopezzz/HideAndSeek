plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.hideandseek.host.api"
    )
}

dependencies {
    implementation(Libraries.Navigation.cicerone)
}