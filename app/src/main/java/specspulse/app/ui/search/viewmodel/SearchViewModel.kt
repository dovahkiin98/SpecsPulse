package specspulse.app.ui.search.viewmodel

import androidx.compose.runtime.mutableStateOf
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
class SearchViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _devices = MutableStateFlow<UIState<List<Device>>?>(null)
    val devices = _devices.asStateFlow()

    var searchTerm = mutableStateOf("")

    fun applySearch(term: String) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _devices.emit(UIState.Loading())

            try {
                val searchResponse = repository.searchDevices(term)

                _devices.emit(UIState.Success(searchResponse))
            } catch (e: Exception) {
                _devices.emit(UIState.Failure(e))
            }
        }
    }
}