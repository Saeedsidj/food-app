package mobin.shabanifar.foodpart.viewmodel

import android.util.Log
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
import mobin.shabanifar.foodpart.data.models.edit_profile.EditUserBody
import mobin.shabanifar.foodpart.data.models.edit_profile.EditUserResponse
import mobin.shabanifar.foodpart.data.network.EditUserApi
import mobin.shabanifar.foodpart.data.network.LogoutApi
import mobin.shabanifar.foodpart.data.stored.UserSessionManager
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editUserApi: EditUserApi, private val logoutApi: LogoutApi, val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _editUserResult = MutableStateFlow<Result>(Result.Idle)
    val editUserResult: SharedFlow<Result> = _editUserResult.asSharedFlow()

     private val _logoutUserResult = MutableStateFlow<Result>(Result.Idle)
    val logoutUserResult: SharedFlow<Result> = _logoutUserResult.asSharedFlow()

    private val _editUserResponse = MutableStateFlow(EditUserResponse(EditUserResponse.AdditionalInfo(""),"" ))
    val editUserResponse: StateFlow<EditUserResponse> = _editUserResponse.asStateFlow()

    fun postEditUser(editUserBody: EditUserBody) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(call = {
                editUserApi.postEditUser(editUserBody)
            }) {
                _editUserResponse.value = it
                Log.d("TAGGG", _editUserResponse.value.additionalInfo?.token.toString())
            }.collect(_editUserResult)
        }
    }
    fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(call = {
                logoutApi.getLogOutUser()
            }) {
            }.collect(_logoutUserResult)
        }
    }

    // fun for save data from saveUserInfo
    fun editUserToken(token: String) {
        viewModelScope.launch {
            userSessionManager.editUserToken(token)
        }
    }

    fun editUsername(userName: String) {
        viewModelScope.launch {
            userSessionManager.editUsername(userName)
        }
    }

    fun clearUserInfo() {
        userSessionManager.clearUserInfo()
    }

}