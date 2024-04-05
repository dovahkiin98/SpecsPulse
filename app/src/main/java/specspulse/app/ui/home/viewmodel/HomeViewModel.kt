package specspulse.app.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import specspulse.app.data.UIState
import specspulse.app.data.repo.Repository
import specspulse.app.model.Device
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _devices = MutableStateFlow<UIState<List<Device>>>(UIState.Loading())
    val devices = _devices.asStateFlow()

    init {
        getMostPopular()
    }

    fun getMostPopular() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _devices.emit(UIState.Loading())

            try {
                val mostPopular = repository.getMostPopular()

                _devices.emit(UIState.Success(mostPopular))
            } catch (e: Exception) {
                _devices.emit(UIState.Failure(e))
            }
        }
    }
}