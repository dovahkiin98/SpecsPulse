package specspulse.app.data

import android.content.Context
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import specspulse.app.model.Device
import specspulse.app.model.Info
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object SpecsUtils {
    private const val link = "http://specspulse.aba.ae/"
    private lateinit var service: SpecsService
    private const val imagesLink = "${link}images/"
    private var isInit = false

    fun init(context: Context) {
        if (isInit) return

        val cache = Cache(File(context.cacheDir, "cache"), 10 * 1024 * 1024)

        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                var response: Response? = null

                while (response == null) {
                    try {
                        response = it.proceed(request)
                    } catch (e: IOException) {
                        Thread.sleep(1000)
                    }
                }
                response
            }.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .build()

        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(link)
            .build().create(SpecsService::class.java)

        Picasso.setSingletonInstance(
            Picasso.Builder(context)
                .downloader(OkHttp3Downloader(client))
                .build()
        )

        isInit = true
    }

    fun devicesList(listener: (List<Device>) -> Unit): Disposable = service.getDeviceList()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { listener(it) }

    fun devicesListLimit(listener: (List<Device>) -> Unit): Disposable = service.getDeviceListLimit()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { listener(it) }

    fun deviceInfo(name: String, listener: (List<Info>) -> Unit): Disposable = service.getInfo(name)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { listener(it) }

    fun imageUri(name: String) = "$imagesLink/$name.jpg"

    fun loadImage(imageUri: String, imageView: ImageView) {
        Picasso.get()
            .load(imageUri)
            .into(imageView)
    }
}