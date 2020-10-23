package specspulse.app.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import specspulse.app.data.SpecsUtils
import specspulse.app.model.Device

class DevicesViewModel(app: Application) : AndroidViewModel(app) {
    val data = DevicesLiveData()

    init {
        println()
    }

    inner class DevicesLiveData : MutableLiveData<List<Device>>() {
        init {
            SpecsUtils.devicesListLimit({
                postValue(it)
            }, {

            })
        }
    }
}