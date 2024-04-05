package specspulse.app.ui.details.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import specspulse.app.model.DeviceDetails
import specspulse.app.model.DeviceSpecsSection
import specspulse.app.theme.AppTheme

@Composable
fun DeviceDetailsSection(
    deviceDetail: DeviceSpecsSection,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RectangleShape,
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                deviceDetail.title,
                modifier = Modifier
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
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
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                        ),
                    )

                    Text(
                        it.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Light",
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun DeviceDetailsSection_Preview() {
    AppTheme {
        DeviceDetailsSection(
            deviceDetail = DeviceDetails.dummyDeviceDetails.details[0],
        )
    }
}