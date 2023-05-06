package me.sulatskovalex.twallet.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import me.sulatskovalex.twallet.common.Res

@Composable
fun StartScreen(
    onCreateWalletClick: () -> Unit,
    onInputSeedClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            modifier = Modifier.size(80.dp).align(Alignment.Center),
            painter = painterResource(Res.image.ic_ton),
            contentDescription = null,
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCreateWalletClick,
            ) {
                Text(Res.string.create_wallet)
            }
            Spacer(Modifier.height(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onInputSeedClick,
            ) {
                Text(Res.string.input_seed)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}