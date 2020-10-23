package specspulse.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.google.android.gms.ads.MobileAds
import specspulse.app.data.SpecsUtils

class SpecsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        SpecsUtils.init(this)

        NIGHT_MODE = SpecsUtils.preferences.getBoolean("Night Mode", false)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        AppCompatDelegate.setDefaultNightMode(if (NIGHT_MODE) MODE_NIGHT_YES else MODE_NIGHT_NO)
//        MobileAds.initialize(this, "ca-app-pub-9586068579198360~5325001927")
    }

    companion object {
        var NIGHT_MODE = false
        val KeepSynced = !BuildConfig.ADS
        var showAds = BuildConfig.ADS
    }
}