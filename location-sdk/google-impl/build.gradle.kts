plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.location.google"
    )
}

dependencies {
    implementation(project(Project.LocationSDK.api))

    implementation(Libraries.coroutines)
    implementation(Libraries.Koin.koinAndroid)

    implementation(Libraries.Google.location)
}