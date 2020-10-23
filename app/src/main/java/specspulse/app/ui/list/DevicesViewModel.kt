package specspulse.app.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import specspulse.app.data.Repository
import specspulse.app.model.Device

class DevicesViewModel(app: Application) : AndroidViewModel(app) {
    val devices = MutableLiveData<List<Device>>()

    init {
        getMostPopular()
    }

    fun getMostPopular() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            try {
                val mostPopular = Repository.getMostPopular()

                devices.postValue(mostPopular)
            } catch (e: Exception) {
                println()
            }
        }
    }
}