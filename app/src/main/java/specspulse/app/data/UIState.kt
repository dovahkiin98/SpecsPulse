package specspulse.app.data

sealed class UIState<T> {
    open class Success<T>(val data: T) : UIState<T>()

    class Failure<T>(val error: Throwable) : UIState<T>()

    class Loading<T> : UIState<T>()

    override fun toString(): String = when (this) {
        is Loading -> "Loading[]"
        is Success<*> -> "Success[data: $data]"
        is Failure -> "Failure[${error.message ?: error.toString()}]"
    }
}