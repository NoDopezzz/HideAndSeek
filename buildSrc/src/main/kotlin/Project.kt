object Project {

    object Features {

        const val mainImpl = ":features:mainmenu:impl"

        const val mainApi = ":features:mainmenu:api"

        const val sessionSearchImpl = ":features:sessionsearch:impl"

        const val sessionSearchApi = ":features:sessionsearch:api"

        const val hostingImpl = ":features:hosting:impl"

        const val hostingApi = ":features:hosting:api"

    }

    object Core {

        const val arch = ":core:arch"

        object Utils {

            const val permissions = ":core:utils:permissions"

        }

        object UI {

            const val compose = ":core:ui:compose"

            const val button = ":core:ui:button"

            const val topbar = ":core:ui:topbar"

        }

    }

    object BluetoothSDK {

        const val scannerImpl = ":bluetooth-sdk:scanner:impl"

        const val scannerApi = ":bluetooth-sdk:scanner:api"

    }

}
