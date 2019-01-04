package specspulse.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.google.android.gms.ads.MobileAds
import org.jetbrains.anko.defaultSharedPreferences
import specspulse.app.data.SpecsUtils

class SpecsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NIGHT_MODE = defaultSharedPreferences.getBoolean("Night Mode", false)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        AppCompatDelegate.setDefaultNightMode(if (NIGHT_MODE) MODE_NIGHT_YES else MODE_NIGHT_NO)
        MobileAds.initialize(this, "ca-app-pub-9586068579198360~5325001927")

        SpecsUtils.init(this)
    }

    companion object {
        var NIGHT_MODE = false
        val KeepSynced = !BuildConfig.ADS
        var showAds = BuildConfig.ADS
    }
}