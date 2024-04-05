package specspulse.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf

inline fun <reified T : Activity> Context.startActivity(
    vararg args: Pair<String, Any>,
    options: Bundle? = null
) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundleOf(*args))
    }, options)
}

inline fun <reified T : Activity> Context.startActivity(extras: Bundle?) {
    startActivity(Intent(this, T::class.java).apply {
        if (extras != null) putExtras(extras)
    }, extras)
}