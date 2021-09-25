package specspulse.app.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import specspulse.app.model.DeviceDetailsSection
import specspulse.app.utils.parseAnnotated

@Composable
fun DeviceDetailsSection(
    deviceDetail: DeviceDetailsSection,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RectangleShape,
        modifier = modifier,
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                deviceDetail.title,
                modifier = Modifier
                    .padding(16.dp),
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            Divider(
                color = MaterialTheme.colors.primary,
            )

            deviceDetail.details.forEach {
                Column(
                    modifier = Modifier
                        .padding(
                            top = if (it.title.isNotBlank()) 16.dp else 0.dp,
                            bottom = 16.dp,
                        )
                        .padding(
                            horizontal = 16.dp,
                        )
                ) {
                    if (it.title.isNotBlank()) Text(
                        it.title,
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onSurface.copy(
                                alpha = ContentAlpha.high,
                            ),
                            fontSize = 16.sp,
                        ),
                    )

                    Text(
                        it.contentText.parseAnnotated(),
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onSurface.copy(
                                alpha = ContentAlpha.medium,
                            )
                        ),
                    )
                }
            }
        }
    }
}