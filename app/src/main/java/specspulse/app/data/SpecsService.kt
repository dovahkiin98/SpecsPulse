package specspulse.app.data

import io.reactivex.Flowable
import specspulse.app.model.Device
import specspulse.app.model.Info
import retrofit2.http.GET
import retrofit2.http.Path

interface SpecsService {
    @GET("DBLimit.php")
    fun getDeviceListLimit(): Flowable<List<Device>>

    @GET("DBSearch.php")
    fun getDeviceList(): Flowable<List<Device>>

    @GET("devices/{device}.json")
    fun getInfo(@Path("device") device: String): Flowable<List<Info>>
}