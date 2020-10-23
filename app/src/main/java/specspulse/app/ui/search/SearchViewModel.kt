package specspulse.app.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import specspulse.app.data.Repository
import specspulse.app.model.Device

class SearchViewModel : ViewModel() {

    val devices = MutableLiveData<List<Device>>()

    fun applySearch(term: String) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            try {
                val searchResponse = Repository.searchDevices(term)

                devices.postValue(searchResponse)
            } catch (e: Exception) {
                println()
            }
        }
    }
}