package specspulse.app.ui.details

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import specspulse.app.data.Repository
import specspulse.app.data.UIState
import specspulse.app.model.Device
import specspulse.app.model.DeviceDetail
import specspulse.app.model.DeviceDetailsSection

class DetailsViewModel(
    private val deviceLink: String,
) : ViewModel() {
    private val _device = MutableLiveData<UIState>()
    val device get() = _device as LiveData<UIState>

    init {
        getDeviceDetails()
    }

    fun getDeviceDetails() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _device.postValue(UIState.Loading)

            try {
                val deviceResponse = Repository.getInstance().getDeviceDetails(deviceLink)

                val sections = mutableListOf<DeviceDetailsSection>()

                var currentTitle = deviceResponse.details.first().sectionName
                val detailsList = mutableListOf<DeviceDetail>()

                deviceResponse.details.forEach {
                    if (currentTitle != it.sectionName) {
                        sections += DeviceDetailsSection(
                            currentTitle,
                            ArrayList(detailsList)
                        )

                        currentTitle = it.sectionName

                        detailsList.clear()
                    }

                    detailsList += it
                }

                _device.postValue(DeviceDetailsSuccessState(sections))
            } catch (e: Exception) {
                _device.postValue(UIState.Failure(e))
            }
        }
    }

    class DeviceDetailsSuccessState(
        `data`: List<DeviceDetailsSection>
    ) : UIState.Success<List<DeviceDetailsSection>>(`data`)

    class Factory(
        private val deviceLink: String,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(
            modelClass: Class<T>,
        ): T {
            if (modelClass.name == DetailsViewModel::class.java.name) {
                return DetailsViewModel(deviceLink) as T
            }

            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}