package specspulse.app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import specspulse.app.data.Repository
import specspulse.app.data.UIState

class SearchViewModel : ViewModel() {
    private val _devices = MutableLiveData<UIState>()
    val devices get() = _devices as LiveData<UIState>

    fun applySearch(term: String) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _devices.postValue(UIState.Loading)

            try {
                val searchResponse = Repository.getInstance().searchDevices(term)

                _devices.postValue(UIState.Success(searchResponse))
            } catch (e: Exception) {
                _devices.postValue(UIState.Failure(e))
            }
        }
    }
}