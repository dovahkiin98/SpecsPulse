package specspulse.app.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import android.widget.ImageView
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import specspulse.app.model.Device
import specspulse.app.model.Info
import java.io.File
import java.util.concurrent.TimeUnit

object SpecsUtils {
    private const val link = "http://specspulse.aba.ae/"
    private lateinit var service: SpecsService
    private const val imagesLink = "${link}images/"
    private var isInit = false
    lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        if (isInit) return

        val cache = Cache(File(context.cacheDir, "cache"), 10 * 1024 * 1024)

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .build()

        service = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl(link)
            .build()
            .create()

        preferences = PreferenceManager.getDefaultSharedPreferences(context)

        isInit = true
    }

    fun devicesList(listener: (List<Device>) -> Unit, onError: (Throwable) -> Unit) = GlobalScope.launch(Dispatchers.Main) {
        try {
            val devices = withContext(Dispatchers.IO) { service.getDeviceList() }
            listener(devices)
        } catch (e: Exception) {
            onError(e)
        }
    }

    fun devicesListLimit(listener: (List<Device>) -> Unit, onError: (Throwable) -> Unit) = GlobalScope.launch(Dispatchers.Main) {
        try {
            val devices = withContext(Dispatchers.IO) { service.getDeviceListLimit() }
            listener(devices)
        } catch (e: Exception) {
            onError(e)
        }
    }

    fun deviceInfo(deviceName: String, listener: (List<Info>) -> Unit, onError: (Throwable) -> Unit) = GlobalScope.launch(Dispatchers.Main) {
        try {
            val deviceInfo = withContext(Dispatchers.IO) { service.getInfo(deviceName) }
            listener(deviceInfo)
        } catch (e: Exception) {
            onError(e)
        }
    }

    fun imageUri(name: String) = "$imagesLink/$name.jpg"

    fun loadImage(imageUri: String, imageView: ImageView) {
        imageView.load(imageUri)
    }
}