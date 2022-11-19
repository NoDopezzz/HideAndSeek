plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.location.api"
    )
}

dependencies {
    implementation(Libraries.Androidx.annotation)
    implementation(Libraries.coroutines)
}