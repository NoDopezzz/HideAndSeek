plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "nay.kirill.hideandseek"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(Libraries.Androidx.core)
    implementation(Libraries.Androidx.appCompat)
    implementation(Libraries.Google.material)
    implementation(Libraries.Androidx.constraint)
}