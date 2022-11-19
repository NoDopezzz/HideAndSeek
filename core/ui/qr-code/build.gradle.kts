plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.core.ui.qrcode",
            compose = true
    )
}

dependencies {
    implementation(Libraries.UI.zxing)
    implementation(Libraries.UI.zxingEmbedded) {
        isTransitive = false
    }

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)

    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(project(Project.Core.UI.compose))
}