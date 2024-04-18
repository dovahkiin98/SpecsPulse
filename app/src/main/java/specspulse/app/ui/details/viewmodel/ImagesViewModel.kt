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
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val deviceTag = savedStateHandle.get<String>("tag")!!
    private val deviceId = savedStateHandle.get<Int>("id")!!
    private val _deviceName = MutableStateFlow(
        savedStateHandle.get<String?>("name"),
    )

    private val _images = MutableStateFlow<UIState<List<String>>>(UIState.Loading())
    val images = _images.asStateFlow()

    private val imagesLink get() = "$deviceTag-pictures-$deviceId"

    val deviceName = _deviceName.asStateFlow()

    init {
        getDeviceImages()
        println(savedStateHandle.toString())
    }

    fun getDeviceImages() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _images.emit(UIState.Loading())

            try {
                val images = repository.getDeviceImages(imagesLink)

                _images.emit(UIState.Success(images))
            } catch (e: Exception) {
                _images.emit(UIState.Failure(e))
            }
        }
    }
}