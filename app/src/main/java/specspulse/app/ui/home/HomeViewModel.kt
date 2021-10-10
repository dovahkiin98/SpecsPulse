package specspulse.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import specspulse.app.data.Repository
import specspulse.app.data.UIState
import specspulse.app.model.Device

class HomeViewModel : ViewModel() {
    private val _devices = MutableLiveData<UIState>()
    val devices get() = _devices as LiveData<UIState>

    init {
        getMostPopular()
    }

    fun getMostPopular() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _devices.postValue(UIState.Loading)

            try {
                val mostPopular = Repository.getInstance().getMostPopular()

                _devices.postValue(UIState.Success(mostPopular))
            } catch (e: Exception) {
                _devices.postValue(UIState.Failure(e))
            }
        }
    }
}