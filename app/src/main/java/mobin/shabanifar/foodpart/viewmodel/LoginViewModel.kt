package mobin.shabanifar.foodpart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.login_response.LoginResponse
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpBody
import mobin.shabanifar.foodpart.data.network.LoginApi
import mobin.shabanifar.foodpart.data.stored.UserSessionManager
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginApi: LoginApi, private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Result>(Result.Idle)
    val loginResult: SharedFlow<Result> = _loginResult.asSharedFlow()

    private val _loginResponse = MutableStateFlow(
        LoginResponse(LoginResponse.Data("", LoginResponse.Data.User("", "", "")))
    )
    val loginResponse: StateFlow<LoginResponse> = _loginResponse.asStateFlow()

    fun postUserLogin(signUpBody: SignUpBody) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(call = {
                loginApi.postUserLogin(signUpBody)
            }) {
                _loginResponse.value = it
            }.collect(_loginResult)
        }
    }

    // fun for save data from saveUserInfo
    fun saveUserInfo(token: String, userName: String, userImage: String, userId: String) {
        viewModelScope.launch {
            userSessionManager.saveUserInfo(token, userName, userImage,userId)
        }
    }
}