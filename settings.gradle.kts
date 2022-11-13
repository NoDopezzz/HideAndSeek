pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HideAndSeek"

include(
        ":app",

        // Features
        ":features:mainmenu:impl",
        ":features:mainmenu:api",
        ":features:sessionsearch:api",
        ":features:sessionsearch:impl",

        // SDK
        ":bluetooth-sdk:scanner:api",
        ":bluetooth-sdk:scanner:impl",

        // Core
        ":core:ui:compose",
        ":core:ui:button",
        ":core:ui:topbar",
        ":core:utils:permissions",
        ":core:arch"
)
