package specspulse.app.ui.search.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import specspulse.app.R
import specspulse.app.theme.AppTheme

@Composable
fun SearchTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit = {},
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface,
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSubmit() },
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        singleLine = true,
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (value.isBlank()) {
                        Text(
                            stringResource(id = R.string.device_name),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.38f,
                                ),
                            ),
                        )
                    }

                    it()
                }

                if (value.isNotBlank()) {
                    IconButton(
                        onClick = {
                            onValueChanged("")
                        },
                    ) {
                        Icon(
                            Icons.Default.Close,
                            null,
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview_Empty() {
    AppTheme {
        TopAppBar(
            title = {
                SearchTextField(
                    value = "",
                    onValueChanged = {},
                    onSubmit = {},
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview_Filled() {
    AppTheme {
        TopAppBar(
            title = {
                SearchTextField(
                    value = "This is an example",
                    onValueChanged = {},
                    onSubmit = {},
                )
            }
        )
    }
}