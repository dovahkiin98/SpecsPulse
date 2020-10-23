package specspulse.app.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import specspulse.app.data.Repository
import specspulse.app.model.Device

class DetailsViewModel : ViewModel() {

    val device = MutableLiveData<Device>()

    fun getData(link: String) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            try {
                val deviceResponse = Repository.getDeviceDetails(link)

                device.postValue(deviceResponse)
            } catch (e: Exception) {
                println()
            }
        }
    }
}