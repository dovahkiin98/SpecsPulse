package specspulse.app.data.remote

import specspulse.app.model.Device
import specspulse.app.model.DeviceDetails

interface RemoteDataSource {
    suspend fun getDeviceDetails(deviceLink: String): DeviceDetails

    suspend fun getDeviceImages(imagesLink: String): List<String>

    suspend fun search(term: String): List<Device>
}