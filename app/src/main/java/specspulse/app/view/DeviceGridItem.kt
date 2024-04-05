package specspulse.app.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import specspulse.app.model.Device
import specspulse.app.theme.AppTheme

@Composable
fun DeviceGridItem(
    device: Device,
    onClick: (Device) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = {
            onClick(device)
        },
        modifier = modifier,
    ) {
        Box {
            AsyncImage(
                model = device.image,
                contentDescription = "${device.name} Image",
                modifier = Modifier
                    .fillMaxSize(),
            )

            Spacer(
                Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(deviceGridItemGradient())
                    .align(Alignment.TopCenter)
            )

            Spacer(
                Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(deviceGridItemGradient())
                    .align(Alignment.BottomCenter)
                    .rotate(180f)
            )

            Text(
                device.name,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        bottom = 8.dp
                    ),
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    }
}

fun deviceGridItemGradient() = Brush.verticalGradient(
    colors = listOf(
        Color(0x99000000),
        Color(0x00000000),
    ),
)

@Preview(name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DeviceGridItem_Preview() {
    AppTheme {
        DeviceGridItem(
            device = Device.dummyDevice,
            onClick = {

            },
            modifier = Modifier
                .height(220.dp)
                .width(172.dp)
        )
    }
}