package specspulse.app.ui.details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import specspulse.app.data.UIState
import specspulse.app.data.repo.Repository
import specspulse.app.model.DeviceDetails
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _device = MutableStateFlow<UIState<DeviceDetails>>(UIState.Loading())
    val device = _device.asStateFlow()

    private val deviceLink = savedStateHandle.get<String>("deviceLink")!!
    val deviceName = savedStateHandle.get<String>("deviceName")!!

    init {
        getDeviceDetails()
    }

    fun getDeviceDetails() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _device.emit(UIState.Loading())

            try {
                val device = repository.getDeviceDetails(deviceLink)

                _device.emit(UIState.Success(device))
            } catch (e: Exception) {
                _device.emit(UIState.Failure(e))
            }
        }
    }
}