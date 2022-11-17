plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.location.huawei"
    )
}

dependencies {
    implementation(project(Project.LocationSDK.api))

    implementation(Libraries.Huawei.location)

    implementation(Libraries.coroutines)
    implementation(Libraries.Koin.koinAndroid)
}