/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class SingleLiveEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     * use it to handle singleLiveEvent
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     * use it when using additional observer like logger
     */
    fun peekContent(): T = content
}
