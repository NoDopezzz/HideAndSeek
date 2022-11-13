plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.core.utils.permissions"
    )
}

dependencies {
    implementation(Libraries.Androidx.core)
}