package specspulse.app.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun View.takeScreenshot() {
    val file = File(
        "${Environment.getExternalStorageDirectory()}/Specspulse/${
            DateFormat.format(
            "dd_MM_yyyy__hh:mm:ss",
            Calendar.getInstance()
        )}.jpg"
    )
    FileOutputStream(file).use {
        drawToBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)

        ActivityCompat.startActivity(context, Intent(Intent.ACTION_SEND).apply {
            type = "image/jpg"
            putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }, null)
        it.flush()
    }
}

val Activity.statusBarHeight
    get() =
        resources.getIdentifier("status_bar_height", "dimen", "android").run {
            if (this > 0) resources.getDimensionPixelSize(this)
            else 0
        }