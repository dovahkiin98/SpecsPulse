package specspulse.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import specspulse.app.R
import specspulse.app.theme.AppTheme

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: String? = null,
    onRetryClick: (() -> Unit)? = null,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            Icons.Outlined.Info,
            contentDescription = null,
            modifier = Modifier
                .size(156.dp),
        )

        Text(
            text = message ?: stringResource(id = R.string.text_placeholder_error),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(
                    vertical = 16.dp
                )
        )

        if(onRetryClick != null) {
            OutlinedButton(
                onClick = onRetryClick,
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 36.dp,
                ),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.retry),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview(
    name = "Error View",
    showBackground = true,
)
@Composable
fun ErrorView() {
    AppTheme {
        ErrorView(
            message = "This is an example of a long error message to see if it wraps or clips",
        ) {

        }
    }
}

@Preview(
    name = "Error View No Retry",
    showBackground = true,
)
@Composable
fun ErrorView_NoRetry() {
    AppTheme {
        ErrorView(
            message = "This is an example of a long error message to see if it wraps or clips",
        )
    }
}