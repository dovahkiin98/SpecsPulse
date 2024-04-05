package specspulse.app.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import specspulse.app.theme.AppTheme

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(vertical = 32.dp)
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(
    name = "Loading View",
    showBackground = true,
)
@Composable
fun LoadingViewPreview() {
    AppTheme {
        LoadingView()
    }
}