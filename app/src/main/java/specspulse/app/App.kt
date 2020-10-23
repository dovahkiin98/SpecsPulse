package specspulse.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import specspulse.app.data.Repository

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Repository.init(this)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}