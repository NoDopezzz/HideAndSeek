plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.core.ui.qrcode"
    )
}

dependencies {
    implementation(Libraries.UI.zxing)
}