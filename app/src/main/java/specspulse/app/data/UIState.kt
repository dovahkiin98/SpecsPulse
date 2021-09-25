package specspulse.app.data

sealed class UIState {
    open class Success<T>(val data: T) : UIState()

    class Failure(val error: Throwable) : UIState() {
        override fun toString() = error.message ?: error.toString()
    }

    object Loading : UIState()

    override fun toString(): String = when (this) {
        is Loading -> "Loading[]"
        is Success<*> -> "Success[data: $data]"
        is Failure -> error.message ?: error.toString()
    }
}