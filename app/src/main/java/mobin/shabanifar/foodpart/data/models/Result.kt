package mobin.shabanifar.foodpart.data.models

sealed class Result {
    object Idle : Result()
    object Loading : Result()
    data class Error(val code: Int,val message :String) : Result()
    object Success : Result()
}