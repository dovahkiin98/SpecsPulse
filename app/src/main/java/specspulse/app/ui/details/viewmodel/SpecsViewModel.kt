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
class SpecsViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val deviceTag = savedStateHandle.get<String>("tag")!!
    private val deviceId = savedStateHandle.get<Int>("id")!!
    private val _deviceName = MutableStateFlow(
        savedStateHandle.get<String?>("name"),
    )

    private val detailsLink get() = "$deviceTag-$deviceId"
    private val _device = MutableStateFlow<UIState<DeviceDetails>>(UIState.Loading())
    val device = _device.asStateFlow()

    val deviceName = _deviceName.asStateFlow()

    init {
        getDeviceDetails()

        println(savedStateHandle.toString())
    }

    fun getDeviceDetails() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _device.emit(UIState.Loading())

            try {
                val device = repository.getDeviceDetails(detailsLink)

                _deviceName.value = device.name

                _device.emit(UIState.Success(device))
            } catch (e: Exception) {
                _device.emit(UIState.Failure(e))
            }
        }
    }
}