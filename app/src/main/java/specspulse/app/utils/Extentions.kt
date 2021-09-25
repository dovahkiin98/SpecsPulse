package specspulse.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf

inline fun consume(code: () -> Unit): Boolean {
    code.invoke()
    return true
}

inline fun <reified T : Activity> Context.startActivity(vararg args: Pair<String, Any>, options: Bundle? = null) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundleOf(*args))
    }, options)
}

inline fun <reified T : Activity> Context.startActivity(extras: Bundle?) {
    startActivity(Intent(this, T::class.java).apply {
        if(extras != null) putExtras(extras)
    }, extras)
}