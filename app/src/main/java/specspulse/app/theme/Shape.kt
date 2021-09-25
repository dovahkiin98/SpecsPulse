package specspulse.app.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun shapes() = Shapes(
    small = RoundedCornerShape(
        4.dp,
    ),
    medium = RoundedCornerShape(
        8.dp,
    ),
    large = RoundedCornerShape(
        8.dp,
    ),
)