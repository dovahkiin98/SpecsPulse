package specspulse.app.data

import retrofit2.http.GET
import retrofit2.http.Path
import specspulse.app.model.Device
import specspulse.app.model.Info

interface SpecsService {
    @GET("DBLimit.php")
    suspend fun getDeviceListLimit(): List<Device>

    @GET("DBSearch.php")
    suspend fun getDeviceList(): List<Device>

    @GET("devices/{device}.json")
    suspend fun getInfo(@Path("device") device: String): List<Info>
}