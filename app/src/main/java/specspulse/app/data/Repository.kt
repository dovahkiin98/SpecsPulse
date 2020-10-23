package specspulse.app.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object Repository {
    private var isInit = false
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        if (isInit) return

        preferences = PreferenceManager.getDefaultSharedPreferences(context)

        isInit = true
    }

    suspend fun getMostPopular() = JsoupRepo.search("")

    suspend fun searchDevices(term: String) = JsoupRepo.search(term)

    suspend fun getDeviceDetails(link: String) = JsoupRepo.getDevice(link)
}