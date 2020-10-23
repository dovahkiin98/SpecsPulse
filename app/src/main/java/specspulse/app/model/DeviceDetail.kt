package specspulse.app.model

import androidx.core.text.HtmlCompat
import specspulse.app.utils.fromHtml

data class DeviceDetail (
    val sectionName: String,
    val title: String,
    val text: String,
) {
    var link: String = ""
    var termLink: String = ""

    val contentText get() = fromHtml(text)

    override fun toString() = title
}