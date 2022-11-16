pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
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
        ":bluetooth-sdk:server:manager",
        ":bluetooth-sdk:server:callback",
        ":bluetooth-sdk:server:service",
        ":bluetooth-sdk:utils",
        ":bluetooth-sdk:client:manager",
        ":bluetooth-sdk:client:service",
        ":bluetooth-sdk:client:callback",

        // Core
        ":core:ui:compose",
        ":core:ui:res",
        ":core:ui:button",
        ":core:ui:topbar",
        ":core:ui:list",
        ":core:utils:permissions",
        ":core:arch",
        ":core:utils:callbackFlow"
)
