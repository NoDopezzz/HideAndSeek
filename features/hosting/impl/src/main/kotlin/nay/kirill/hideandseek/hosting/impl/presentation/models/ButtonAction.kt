package nay.kirill.hideandseek.hosting.impl.presentation.models

import nay.kirill.hideandseek.hosting.impl.R
import nay.kirill.core.ui.res.R as CoreR

internal sealed interface ButtonAction {

    val buttonTitleId: Int

    object Retry : ButtonAction {

        override val buttonTitleId: Int = CoreR.string.retry_button

    }

    object Back : ButtonAction {

        override val buttonTitleId: Int = CoreR.string.back_button

    }

    object Start : ButtonAction {

        override val buttonTitleId: Int = R.string.primary_button_title

    }

}
