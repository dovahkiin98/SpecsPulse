package specspulse.app.data

sealed class UIState {
    open class Success<out T>(val data: T) : UIState()

    class Failure(val error: Throwable) : UIState()

    object Loading : UIState()

    override fun toString(): String = when (this) {
        is Loading -> "Loading[]"
        is Success<*> -> "Success[data: $data]"
        is Failure -> error.message ?: error.toString()
    }
}