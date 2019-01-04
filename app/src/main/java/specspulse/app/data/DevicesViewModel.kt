package specspulse.app.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import specspulse.app.model.Device

class DevicesViewModel(app: Application) : AndroidViewModel(app) {
    val data = DevicesLiveData()

    init {
        println()
    }

    inner class DevicesLiveData : LiveData<List<Device>>() {
        init {
            SpecsUtils.devicesListLimit { value = it }
        }
    }
}