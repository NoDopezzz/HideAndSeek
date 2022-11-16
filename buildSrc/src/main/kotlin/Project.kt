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

            const val callbackFlow = ":core:utils:callbackFlow"

        }

        object UI {

            const val compose = ":core:ui:compose"

            const val res = ":core:ui:res"

            const val button = ":core:ui:button"

            const val topbar = ":core:ui:topbar"

            const val list = ":core:ui:list"

            const val error = ":core:ui:error"

            const val timer = ":core:ui:timer"

        }

    }

    object BluetoothSDK {

        const val scannerImpl = ":bluetooth-sdk:scanner:impl"

        const val scannerApi = ":bluetooth-sdk:scanner:api"

        const val serverManager = ":bluetooth-sdk:server:manager"

        const val serverService = ":bluetooth-sdk:server:service"

        const val serverCallback = ":bluetooth-sdk:server:callback"

        const val serverExceptions = ":bluetooth-sdk:server:exceptions"

        const val clientManager = ":bluetooth-sdk:client:manager"

        const val clientService = ":bluetooth-sdk:client:service"

        const val clientCallback = ":bluetooth-sdk:client:callback"

        const val clientExceptions = ":bluetooth-sdk:client:exceptions"

        const val utils = ":bluetooth-sdk:utils"

        const val messages = ":bluetooth-sdk:messages"

    }

}
