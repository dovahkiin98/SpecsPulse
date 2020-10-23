package specspulse.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import specspulse.app.SpecsApp

inline fun consume(code: () -> Unit): Boolean {
    code.invoke()
    return true
}

fun AdView.showAds() = if (SpecsApp.showAds) {
    adListener = object : AdListener() {
        override fun onAdLoaded() {
            visibility = View.VISIBLE
        }
    }
    loadAd(AdRequest.Builder().build())
} else Unit

fun Resources.toDp(number: Number) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    number.toFloat(),
    displayMetrics,
)

inline fun <reified T : Activity> Context.startActivity(vararg args: Pair<String, Any>, bundle: Bundle? = null) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundleOf(*args))
    }, bundle)
}

fun Context.shortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()