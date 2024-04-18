package specspulse.app.data.repo

import specspulse.app.model.Device
import specspulse.app.model.DeviceDetails

interface Repository {

    suspend fun getMostPopular(): List<Device>

    suspend fun searchDevices(term: String): List<Device>

    suspend fun getDeviceDetails(link: String): DeviceDetails

    suspend fun getDeviceImages(link: String): List<String>
}