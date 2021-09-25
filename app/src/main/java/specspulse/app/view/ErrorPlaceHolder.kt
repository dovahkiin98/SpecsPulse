package specspulse.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import specspulse.app.R

@Composable
fun ErrorPlaceHolder(
    modifier: Modifier = Modifier,
    message: String? = null,
    onRetryClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .size(
                    height = 156.dp,
                    width = 156.dp,
                )
        )

        Text(
            text = message ?: stringResource(id = R.string.text_placeholder_error),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(
                    vertical = 16.dp
                )
        )

        OutlinedButton(
            onClick = onRetryClick,
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 36.dp,
            ),
            modifier = Modifier
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun ErrorPlaceHolder() {
    MaterialTheme {
        ErrorPlaceHolder {

        }
    }
}