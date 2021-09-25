package specspulse.app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import specspulse.app.model.Device
import specspulse.app.utils.parseAnnotated

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun DeviceGridItem(
    device: Device,
    onClick: (Device) -> Unit,
    modifier: Modifier = Modifier,
) {
    val image = rememberImagePainter(device.image)

    Card(
        onClick = {
            onClick(device)
        },
        modifier = modifier,
    ) {
        Box {
            Image(
                painter = image,
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
                device.name.parseAsHtml(HtmlCompat.FROM_HTML_MODE_COMPACT)
                    .parseAnnotated(),
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