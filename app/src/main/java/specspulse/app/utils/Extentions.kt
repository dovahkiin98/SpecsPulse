package specspulse.app.utils

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.text.format.DateFormat
import android.util.TypedValue
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import specspulse.app.SpecsApp
import java.io.File
import java.io.FileOutputStream
import java.util.*

inline fun consume(code: () -> Unit): Boolean {
    code.invoke()
    return true
}

fun View.takeScreenshot() {
    val file = File(
        "${Environment.getExternalStorageDirectory()}/Specspulse/${DateFormat.format(
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

fun AdView.showAds() = if (SpecsApp.showAds) {
    adListener = object : AdListener() {
        override fun onAdLoaded() {
            visibility = View.VISIBLE
        }
    }
    loadAd(AdRequest.Builder().build())
} else Unit

fun Activity.getStatusBarHeight() =
    resources.getIdentifier("status_bar_height", "dimen", "android").run {
        if (this > 0) resources.getDimensionPixelSize(this)
        else 0
    }

fun Number.toDp(res: Resources) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), res.displayMetrics)