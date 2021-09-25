package specspulse.app.utils

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.CharacterStyle
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.text.getSpans

@Composable
fun Spanned.parseAnnotated(): AnnotatedString {
    val spanned = this

    return buildAnnotatedString {
        append(spanned.toString())

        getSpans<CharacterStyle>().forEach {
            val start = spanned.getSpanStart(it)
            val end = spanned.getSpanEnd(it)

            when (it) {
                is StyleSpan -> {
                    when (it.style) {
                        Typeface.BOLD -> addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start,
                            end,
                        )
                        Typeface.ITALIC -> addStyle(
                            SpanStyle(fontStyle = FontStyle.Italic),
                            start,
                            end,
                        )
                        Typeface.BOLD_ITALIC -> addStyle(
                            SpanStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            ),
                            start,
                            end,
                        )
                    }
                }
                is UnderlineSpan -> {
                    addStyle(
                        SpanStyle(textDecoration = TextDecoration.Underline),
                        start,
                        end,
                    )
                }
                is StrikethroughSpan -> {
                    addStyle(
                        SpanStyle(textDecoration = TextDecoration.LineThrough),
                        start,
                        end,
                    )
                }
            }
        }
    }
}