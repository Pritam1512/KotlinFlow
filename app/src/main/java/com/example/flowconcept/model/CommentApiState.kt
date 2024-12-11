// A helper class to handle states
data class CommentApiState(val status: Status, val data: String?, val message: String?) {

    companion object {

        // In case of Success,set status as
        // Success and data as the response
        fun success(data: String): CommentApiState {
            return CommentApiState(Status.SUCCESS, data, null)
        }

        // In case of failure ,set state to Error ,
        // add the error message,set data to null
        fun error(msg: String): CommentApiState {
            return CommentApiState(Status.ERROR, null, msg)
        }

        // When the call is loading set the state
        // as Loading and rest as null
        fun loading(): CommentApiState{
            return CommentApiState(Status.LOADING, null, null)
        }
    }
}

// An enum to store the
// current state of api call
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
