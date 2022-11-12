package nay.kirill.hideandseek.sessionsearch.impl.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nay.kirill.core.compose.AppColors
import nay.kirill.core.compose.AppTextStyle

@Composable
internal fun SessionElement(
        state: SessionUiState,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
) {
    Column(
            modifier = modifier
                    .clickable(
                            onClick = onClick,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                    bounded = true,
                                    color = AppColors.RippleColor
                            )
                    )
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
                modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                    text = state.name,
                    style = AppTextStyle.ListTextStyle,
                    modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.weight(1F))
            if (state.isLoading) {
                CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = AppColors.Primary,
                        strokeWidth = 2.dp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
                modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(color = AppColors.DividerColor)
        )
    }
}