package specspulse.app.utils

import androidx.core.text.HtmlCompat

fun fromHtml(text: String) = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)