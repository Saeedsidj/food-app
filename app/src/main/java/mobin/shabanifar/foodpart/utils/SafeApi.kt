package mobin.shabanifar.foodpart.utils


import android.util.Log
import com.squareup.moshi.Moshi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import mobin.shabanifar.foodpart.data.models.ErrorResponse
import mobin.shabanifar.foodpart.data.models.Result
import retrofit2.Response


suspend fun <T> safeApi(
    call: suspend () -> Response<T>,
    onDataReady: (T) -> Unit,
): Flow<Result> {
    return flow {
        emit(Result.Loading)
        delay(1000)
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (currentCoroutineContext().isActive) {
                        onDataReady(body)
                    }
                    emit(Result.Success)
                } else {
                    emit(Result.Error(response.code(), "whoops body was empty"))
                }
            } else {
                val bodyCode = response.let {
                    response.errorBody()?.source()?.let {
                        val moshiAdapter =
                            Moshi.Builder().build().adapter(ErrorResponse::class.java)
                        moshiAdapter.fromJson(it)
                    }
                }
                val errorBody = when (bodyCode?.data?.code) {
                    "missing_body_field" -> "حداقل یک فیلد را تغییر دهید"
                    "invalid_password" -> "رمز عبور به درستی وارد نشده است"
                    "invalid_credentials" -> "رمز عبور یا نام کاربری به درستی وارد نشده است"
                    else -> ""
                }
                Log.d("TAGGG", bodyCode?.data?.code.toString())
                emit(Result.Error(response.code(), errorBody))
            }
        } catch (t: Throwable) {
            emit(Result.Error(500, "whoops: ${t.message}"))
        }
    }.cancellable()
}



