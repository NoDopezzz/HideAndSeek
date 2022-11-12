plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
}

android {
    applicationConfig(
        target = project,
        targetPackage = "nay.kirill.hideandseek"
    )
}

dependencies {
    implementation(Libraries.Androidx.core)
    implementation(Libraries.Androidx.appCompat)
    implementation(Libraries.Google.material)
    implementation(Libraries.Androidx.constraint)
    implementation(Libraries.Koin.koinAndroid)
    implementation(Libraries.Navigation.cicerone)

    implementation(project(Project.Features.mainImpl))
    implementation(project(Project.Features.mainApi))
}