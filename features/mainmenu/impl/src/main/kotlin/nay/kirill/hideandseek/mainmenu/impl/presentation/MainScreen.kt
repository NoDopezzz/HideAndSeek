package nay.kirill.hideandseek.mainmenu.impl.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nay.kirill.core.compose.AppTextStyle

@Preview
@Composable
fun MainMenuScreen() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Compose main text",
        style = AppTextStyle.H1
    )
}