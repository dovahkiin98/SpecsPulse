package specspulse.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import specspulse.app.data.Repository

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Repository.getInstance()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}