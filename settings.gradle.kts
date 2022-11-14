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
        ":features:hosting:api",
        ":features:hosting:impl",

        // SDK
        ":bluetooth-sdk:scanner:api",
        ":bluetooth-sdk:scanner:impl",

        // Core
        ":core:ui:compose",
        ":core:ui:res",
        ":core:ui:button",
        ":core:ui:topbar",
        ":core:ui:list",
        ":core:utils:permissions",
        ":core:arch"
)
